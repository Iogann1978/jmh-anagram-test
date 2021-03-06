#Создание проекта из архетипа
```shell script
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DgroupId=ru.home -DartifactId=jmh-anagram-test -Dversion=1.0-SNAPSHOT
```

#Анаграммы Властелина колец

Я никогда бы не подумал, что меня может как-то заинтересовать задача вычисления анаграмм в книге "Властелин колец", если 
бы однажды я не пошёл собеседоваться в Спортмастер в стремлении в очередной раз сменить место работы. Мурыжили меня там 
два человека, задавали всякие вопросы, а под конец предложили придумать алгоритм вычисления анаграмм. Ну то есть 
дают тебе два слова, и ты по этому алгоритму должен определить - являются ли они анаграммой или нет. И вот тут я почему-то 
решил выпендриться и предложил совершенно дикий алгоритм, который тут же был подвержен беспощадной критике. Хотя было 
это полтора года назад, я сейчас вдруг вспомнил эту задачу и этот свой дурацкий алгоритм, и решил его всячески протестить.
Алгоритм оказался не просто плохим, а совершенно ужасным.

##Стандартный алгоритм с сортировкой букв в словах.

Допустим, мы пытаемся определить - являются ли слова LADIES и SAILED анаграммой. Для этого мы сортируем буквы в первом 
слове LADIES по алфавиту, и получаем новое слово - ADEILS. Со вторым словом SAILED мы поступаем точно также, и получаем 
то же самое слово - ADEILS. Следовательно, эти слова являются анаграммой.

##Алгоритм с использованием битовой маски и рекурсии

У нас есть два слова - tea и eat. Мы берем значения ASCII-кодов для букв в обоих словах ([116, 101, 97], [101, 97, 116])
и составляем две битовые маски с установленными битами в указанных позициях. Далее мы делаем побитовую операцию XOR для 
этих масок и получаем результирующую битовую маску. И если результирующая маска будет нулевой, то слова являются анаграммой.
Но тут есть один момент, связанный с возможностью повторения букв в слове. Поэтому, перед тем как установить бит в позицию
ASCII-символа, мы сначала смотрим - установлен он там уже или нет. Если не установлен, то устанавливаем, а если установлен,
то записываем повторяющуюся букву в новое слово. Таким образом, у нас получится битовая маска для неповторяющихся букв слова
и новое слово, составленное из повторяющихся букв. Далее для нового слова мы рекурсивно выполняем ту же операцию до тех 
пор, пока повторяющихся букв не останется:

| |
|-------------------------------------------------------------------|
| IMPRESSIONS                                                       |
| IMPRESON (битовая маска) + ISS (новое слово)                      |
| IMPRESON (битовая маска) + IS (битовая маска) + S (новое слово)   |
| IMPRESON (битовая маска) + IS (битовая маска) + S (битовая маска) |

##Алгоритм с использованием списка

Для примера будем сравнивать cлова CHEAT и TEACH. Создаем список для хранения букв (лучше всего подойдет LinkedList). На
первом этапе добавляем каждую букву из слова CHEAT в список. На втором этапе берём каждую букву в слове TEACH и проверяем,
есть ли она в списке. Если есть, то удаляем её из списка. Если в конце получаем пустой список, то слова являются анаграммой.

##Алгоритм с использованием словарей

Возьмем два слова: astronomer и moonstarer. Для каждого слова составим словарь, где ключем будет буква, а значением - 
количество её повторений в слове. Для astronomer получим 
```json
{"a":1, "s":1, "t":1, "r":2, "o":2, "n":1, "m":1, "e":1}
```
для moonstarer:
```json
{"m":1, "o":2, "n":1, "s":1, "t":1, "a":1, "r":2, "e":1}
```
Затем просто сравниваем присутствие каждого ключа одного словаря в другом
и значения для ключей в обоих словарях.

##Алгоритм с суммированием степеней двойки.

Для примера возьмем слова ERICCLAPTON и NARCOLEPTIC. На первом этапе для каждой буквы первого слова сопоставим степень двойки:
```json
{"E0":0, "R0":1, "I0":2, "C0":3, "C1":4, "L0":5, "A0":6, "P0":7, "T0":8, "O0":9, "N0":10}
```
Вычислим первую сумму степеней двойки:\
sum1 = 2^0 + 2^1 + 2^2 + 2^3 + 2^4 + 2^5 + 2^6 + 2^7 + 2^8 + 2^9 + 2^10 = 1 + 2 + 4 + 8 + 16 + 32 + 64 + 128 + 256 + 512 + 1024 = 2047\
На втором этапе для каждой буквы слова NARCOLEPTIC ищем нужную степень двойки в словаре, составленном на предыдущем этапе,
и опять суммируем степени двойки:\
sum2 = 2^10 + 2^6 + 2^1 + 2^3 + 2^9 + 2^5 + 2^0 + 2^7 + 2^8 + 2^2 + 2^ 4 = 2047\
Если sum1 = sum2, то слова являются анаграммой.

##Тестирование алгоритмов на различных входных данных

Для тестирования были проведены 3 прогревочные и 7 измерительных итераций на различных входных данных. Для первого теста
использовались слова из трёх букв: tea, eat, net, ten. Ниже результаты измерений средней производительности для каждого 
метода:

| Benchmark             | Mode | Cnt |    Score |    Error | Units | Place         |
|-----------------------|------|-----|---------:|---------:|-------|---------------|
| MyBenchmark.testBites | avgt |   7 | 1105,399 |    1,579 | ns/op | bronze        |
| MyBenchmark.testList  | avgt |   7 |  812,269 |    3,598 | ns/op | silver        |
| MyBenchmark.testMap   | avgt |   7 | 2584,923 |  100,407 | ns/op |               |
| MyBenchmark.testPow   | avgt |   7 | 6732,986 |   42,013 | ns/op | outsider      |
| MyBenchmark.testSort  | avgt |   7 |  766,540 |    3,166 | ns/op | gold (winner) |

Здесь победителем стал стандартный алгоритм с сортировкой, второе место - алгоритм с использованием списка, и третье - 
алгоритм с битовыми масками. Алгоритм с суммированием степеней двойки показал самый худший результат, в 8 раза медленнее
алгоритма с сортировкой.

Для второго теста брались слова из 9-10 букв: debitcard, badcredit, astronomer, moonstarer.

| Benchmark             | Mode | Cnt |    Score |  Error | Units | Place         |
|-----------------------|------|-----|---------:|-------:|-------|---------------|
| MyBenchmark.testBites | avgt |   7 |  823,765 | 33,129 | ns/op | silver        |
| MyBenchmark.testList  | avgt |   7 |  880,815 |  8,470 | ns/op | bronze        |
| MyBenchmark.testMap   | avgt |   7 | 2662,382 | 17,342 | ns/op |               |
| MyBenchmark.testPow   | avgt |   7 | 9398,769 | 11,741 | ns/op | outsider      |
| MyBenchmark.testSort  | avgt |   7 |  657,251 | 22,152 | ns/op | gold (winner) |

Тут победителем остаётся алгоритм с сортировкой, зато алгоритм с битовыми масками обогнал алгоритм со списком (второе и 
третье место). Последними опять пришли степени двойки - в 14 раз медленнее победителя.

Для третьего теста брались слова из 11-13 букв: BRITNEYSPEARS, PRESBYTERIANS, ERICCLAPTON, NARCOLEPTIC
	
| Benchmark             | Mode |  Cnt |      Score |     Error |  Units | Place         |
|-----------------------|------|------|-----------:|----------:|--------|---------------|
| MyBenchmark.testBites | avgt |    7 |    868,912 |    16,795 |  ns/op | gold (winner) |
| MyBenchmark.testList  | avgt |    7 |   1273,199 |    60,751 |  ns/op | bronze        |
| MyBenchmark.testMap   | avgt |    7 |   3764,739 |   582,770 |  ns/op |               |
| MyBenchmark.testPow   | avgt |    7 |  11994,441 |    98,839 |  ns/op | outsider      |
| MyBenchmark.testSort  | avgt |    7 |    870,905 |   127,134 |  ns/op | silver        |

Наконец произошла смена победителя, хотя отрыв пока почти минимальный. Первое место - битовые маски, второе - сортировка,
третье - список. Степени двойки всё ещё в 14 раз медленнее победителя.

В качестве финального теста была выбрана задача поиска всех анаграмм в книге "Lord Of The Rings", в которой всеми методами
обнаружилось 811 анаграмм среди 14416 уникальных слов. В качестве примера, можно привести такие анаграммы, как ELROND -> NELDOR,
GONDOR -> NOGROD, SWARD -> DRAWS

| Benchmark             | Mode | Cnt |            Score |          Error | Units | Place         |
|-----------------------|------|-----|-----------------:|---------------:|-------|---------------|
| MyBenchmark.testBites | avgt |   7 |   5859232342,286 |   63448344,959 | ns/op | gold (winner) |
| MyBenchmark.testList  | avgt |   7 |   7735548102,643 | 1548463076,439 | ns/op | bronze        |
| MyBenchmark.testMap   | avgt |   7 |  14223285654,429 | 2259397131,758 | ns/op |               |
| MyBenchmark.testPow   | avgt |   7 |  32015980682,714 | 1815711436,155 | ns/op | outsider      |
| MyBenchmark.testSort  | avgt |   7 |   7664257999,429 |   50244874,847 | ns/op | silver        |

Битовые маски уверенно одержали победу, обойдя на 2 секунды своих конкурентов. Сортировку и список я бы поставил на второе
место, хотя формально сортировка оказалась быстрее. Степени двойки стабильно вне игры.

##Вывод

Не нужно выпендриваться на собеседованиях