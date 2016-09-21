# Notes about the project

## The team

- Luca Parisi: luca.parisi.2@outlook.it
- Yaroslav Shatalov: shatalov.yarik@gmail.com

## Comments

First Exercise, Naive scheduler:

Day 20110303
Initialising...
Incomes:    1317,67â‚¬
Penalties:  0,23â‚¬
Energy:     255,60â‚¬
Revenue:    1061,84â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  2,58â‚¬
Energy:     232,60â‚¬
Revenue:    714,67â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  4,09â‚¬
Energy:     249,01â‚¬
Revenue:    447,17â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  22,51â‚¬
Energy:     294,14â‚¬
Revenue:    1145,02â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  107,81â‚¬
Energy:     258,29â‚¬
Revenue:    884,18â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  225,23â‚¬
Energy:     303,30â‚¬
Revenue:    1220,78â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  10,18â‚¬
Energy:     282,14â‚¬
Revenue:    1078,82â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  10,65â‚¬
Energy:     269,97â‚¬
Revenue:    1105,02â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  4,70â‚¬
Energy:     254,03â‚¬
Revenue:    959,90â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  14,20â‚¬
Energy:     244,73â‚¬
Revenue:    735,84â‚¬

hop (Global)
Incomes:    12399,24â‚¬
Penalties:  402,18â‚¬
Energy:     2643,81â‚¬
Revenue:    9353,25â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:34.649s
Finished at: Mon Jan 25 10:44:41 CET 2016
Final Memory: 5M/109M


--------------------------

Second Exercise, Anti-Affinity

Day 20110303
Incomes:    1317,67â‚¬
Penalties:  3,06â‚¬
Energy:     264,74â‚¬
Revenue:    1049,87â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  0,23â‚¬
Energy:     235,34â‚¬
Revenue:    714,27â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  3,13â‚¬
Energy:     253,97â‚¬
Revenue:    443,17â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  16,66â‚¬
Energy:     293,88â‚¬
Revenue:    1151,14â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  27,83â‚¬
Energy:     264,39â‚¬
Revenue:    958,06â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  101,78â‚¬
Energy:     306,80â‚¬
Revenue:    1340,73â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  0,53â‚¬
Energy:     288,16â‚¬
Revenue:    1082,45â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  0,06â‚¬
Energy:     272,53â‚¬
Revenue:    1113,05â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  33,39â‚¬
Energy:     259,49â‚¬
Revenue:    925,75â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  0,81â‚¬
Energy:     247,59â‚¬
Revenue:    746,37â‚¬

hop (Global)
Incomes:    12399,24â‚¬
Penalties:  187,48â‚¬
Energy:     2686,90â‚¬
Revenue:    9524,86â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:35.253s
Finished at: Mon Jan 25 11:22:10 CET 2016
Final Memory: 5M/109M
------------------------------------------------------------------------


2.What is the theoretical complexity of this algorithm ?

The complexity is linear, O(n*m),
where n = number of hosts, m = mean number of virtual machine for each host,
because for each host we have to check every virtual machine,
so we have a nested loop with 2 for.

3.What is the impact of such an algorithm over the cluster hosting capacity ? Why ?

The cluster hosting capacity is decreasing with respect to the naive scheduler,
because we have to spread all the virtual machines that are not "affined".
For example, if we have 2 virtual machines with id 0 and 1,
where vm0 is very big and vm1 is very little, they can't stay on the same host,
so we have to use a new host only for the little virtual machine.

-----------------

Third Exercise:

NextFit Scheduler:

Day 20110303
Incomes:    1317,67â‚¬
Penalties:  0,23â‚¬
Energy:     264,87â‚¬
Revenue:    1052,58â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  2,58â‚¬
Energy:     240,52â‚¬
Revenue:    706,75â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  4,00â‚¬
Energy:     255,22â‚¬
Revenue:    441,05â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  22,46â‚¬
Energy:     303,84â‚¬
Revenue:    1135,38â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  99,34â‚¬
Energy:     264,25â‚¬
Revenue:    886,69â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  178,75â‚¬
Energy:     311,64â‚¬
Revenue:    1258,91â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  10,18â‚¬
Energy:     284,75â‚¬
Revenue:    1076,21â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  10,65â‚¬
Energy:     279,87â‚¬
Revenue:    1095,12â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  4,38â‚¬
Energy:     261,00â‚¬
Revenue:    953,25â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  14,20â‚¬
Energy:     248,31â‚¬
Revenue:    732,27â‚¬

hop (global)
Incomes:    12399,24â‚¬
Penalties:  346,77â‚¬
Energy:     2714,27â‚¬
Revenue:    9338,20â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:17.128s
Finished at: Mon Jan 25 12:16:29 CET 2016
Final Memory: 5M/109M
------------------------------------------------------------------------


Worst Fit Scheduler:

Day 20110303
Initialising...
Incomes:    1317,67â‚¬
Penalties:  0,00â‚¬
Energy:     335,83â‚¬
Revenue:    981,84â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  0,00â‚¬
Energy:     300,65â‚¬
Revenue:    649,20â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  0,00â‚¬
Energy:     308,48â‚¬
Revenue:    391,80â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  0,00â‚¬
Energy:     346,64â‚¬
Revenue:    1115,03â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  3,66â‚¬
Energy:     324,71â‚¬
Revenue:    921,91â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  2,36â‚¬
Energy:     369,07â‚¬
Revenue:    1377,89â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  0,00â‚¬
Energy:     340,40â‚¬
Revenue:    1030,74â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  0,00â‚¬
Energy:     344,06â‚¬
Revenue:    1041,58â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  0,00â‚¬
Energy:     323,83â‚¬
Revenue:    894,79â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  0,02â‚¬
Energy:     303,28â‚¬
Revenue:    691,47â‚¬

hop (global)
Incomes:    12399,24â‚¬
Penalties:  6,06â‚¬
Energy:     3296,95â‚¬
Revenue:    9096,24â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 7:12.903s
Finished at: Fri Jan 29 13:04:02 CET 2016
Final Memory: 4M/64M
------------------------------------------------------------------------

Which algorithms performs the best in terms of reducing the SLA violation. Why ?

The WorstFit scheduler is better than the NextFit scheduler in terms of SLA violations,
because the global penalties of the worstFit are 6,06â‚¬, while the global penalties
of the nextFit are 346,77â‚¬


What is the theoretical complexity of each of the implemented scheduler ?

NextFit Complexity worst case = O(n)
In the worst case, we have to read all the hosts in order to find 
one host for the virtual machine.

NextFit complexity best case = O(1)
If the next host has enough space for the new virtual machine to be scheduled.

WorstFit Complexity = n*log(n)
Because we sort the list of hosts in a decreasing way according to the dimension z
(We assume the average complexity of quick sort algorithm).
Z is the summation of the 2 dimension, RAM and Mips available.
Then we take the first host, that has the maximum value of z.
The complexity is the same both for the worst case and best case.

-----------------

Get rid of SLA violations:

Day 20110303
Initialising...
Incomes:    1317,67â‚¬
Penalties:  0,00â‚¬
Energy:     282,64â‚¬
Revenue:    1035,03â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  0,00â‚¬
Energy:     250,42â‚¬
Revenue:    699,43â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  0,00â‚¬
Energy:     265,61â‚¬
Revenue:    434,66â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  0,00â‚¬
Energy:     320,27â‚¬
Revenue:    1141,41â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  0,00â‚¬
Energy:     279,35â‚¬
Revenue:    970,93â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  0,00â‚¬
Energy:     330,59â‚¬
Revenue:    1418,73â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  0,00â‚¬
Energy:     304,95â‚¬
Revenue:    1066,20â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  0,00â‚¬
Energy:     300,38â‚¬
Revenue:    1085,26â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  0,00â‚¬
Energy:     276,00â‚¬
Revenue:    942,63â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  0,00â‚¬
Energy:     262,02â‚¬
Revenue:    732,76â‚¬

hop
Incomes:    12399,24â‚¬
Penalties:  0,00â‚¬
Energy:     2872,22â‚¬
Revenue:    9527,02â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:16.674s
Finished at: Mon Jan 25 22:08:11 CET 2016
Final Memory: 5M/109M
------------------------------------------------------------------------

-------------------------------------------------

Energy-efficient schedulers

We have implemented a bestFit scheduler that considers these 2 dimensions for each host: Ram and Mips.
The 2 dimensions are summed and then the list of available hosts is ordered in an increasing way,
so the first host of the list is the one who has less (Ram + Mips) available.
The bestFit algorithm is very energy efficient because it tries to use the lowest number 
of hosts in order to fit all the virtual machines.

Day 20110303
Initialising...
Incomes:    1317,67â‚¬
Penalties:  3,43â‚¬
Energy:     252,04â‚¬
Revenue:    1062,19â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  3,13â‚¬
Energy:     230,26â‚¬
Revenue:    716,45â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  4,77â‚¬
Energy:     245,14â‚¬
Revenue:    450,36â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  51,27â‚¬
Energy:     290,47â‚¬
Revenue:    1119,94â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  904,21â‚¬
Energy:     253,81â‚¬
Revenue:    92,25â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  324,12â‚¬
Energy:     298,87â‚¬
Revenue:    1126,32â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  41,87â‚¬
Energy:     277,52â‚¬
Revenue:    1051,75â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  10,70â‚¬
Energy:     266,23â‚¬
Revenue:    1108,71â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  67,34â‚¬
Energy:     249,71â‚¬
Revenue:    901,58â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  14,41â‚¬
Energy:     240,93â‚¬
Revenue:    739,44â‚¬

hop (global)
Incomes:    12399,24â‚¬
Penalties:  1425,26â‚¬
Energy:     2604,99â‚¬
Revenue:    8369,00â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:53.029s
Finished at: Fri Jan 29 13:28:59 CET 2016
Final Memory: 5M/77M
------------------------------------------------------------------------



Greedy scheduler (greedy flag)

We implement the FitFit scheduling algorithm for the greedy algorithm.
the list of available hosts is ordered like in the Energy-efficient scheduler
(with the 2 dimensions Ram and Mips).
Then, we will take the first suitable host, starting from the begin of the list,
so we try to use the lowest number of hosts by putting all the virtual machines
in a suitable host, so there will be no penalty.

The Complexity of this algorithm is:
O(n*log(n)) because we have to sort the list of host
(we suppose that quick sort is the algorithm used for ordering the list of hosts.)

Day 20110303
Initialising...
Incomes:    1317,67â‚¬
Penalties:  0,00â‚¬
Energy:     278,51â‚¬
Revenue:    1039,17â‚¬

Day 20110306
Incomes:    949,85â‚¬
Penalties:  0,00â‚¬
Energy:     251,47â‚¬
Revenue:    698,38â‚¬

Day 20110309
Incomes:    700,27â‚¬
Penalties:  0,00â‚¬
Energy:     259,96â‚¬
Revenue:    440,31â‚¬

Day 20110322
Incomes:    1461,67â‚¬
Penalties:  0,00â‚¬
Energy:     320,12â‚¬
Revenue:    1141,55â‚¬

Day 20110325
Incomes:    1250,28â‚¬
Penalties:  0,00â‚¬
Energy:     276,15â‚¬
Revenue:    974,13â‚¬

Day 20110403
Incomes:    1749,31â‚¬
Penalties:  0,00â‚¬
Energy:     332,05â‚¬
Revenue:    1417,26â‚¬

Day 20110409
Incomes:    1371,14â‚¬
Penalties:  0,00â‚¬
Energy:     304,45â‚¬
Revenue:    1066,70â‚¬

Day 20110411
Incomes:    1385,64â‚¬
Penalties:  0,00â‚¬
Energy:     297,36â‚¬
Revenue:    1088,28â‚¬

Day 20110412
Incomes:    1218,62â‚¬
Penalties:  0,00â‚¬
Energy:     275,10â‚¬
Revenue:    943,52â‚¬

Day 20110420
Incomes:    994,78â‚¬
Penalties:  0,00â‚¬
Energy:     259,28â‚¬
Revenue:    735,50â‚¬

hop
Incomes:    12399,24â‚¬
Penalties:  0,00â‚¬
Energy:     2854,45â‚¬
Revenue:    9544,79â‚¬

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5:09.166s
Finished at: Fri Jan 29 14:37:09 CET 2016
Final Memory: 5M/77M
------------------------------------------------------------------------
