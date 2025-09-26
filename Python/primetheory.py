import random as r

prime = []
not_prime = []

equations = []
max_int = 100

for i in range(2, max_int-1):
    tracker = 1
    for y in range(2, i-1):
        tracker = tracker*(i%y)
    if tracker == 0:
        not_prime.append(i)
    else: prime.append(i)

for int in range(4, 101, 2):
    for number in prime:
        ans = int - number
        if ans in prime:
            equations.append([int, ans, number])
        else: continue

print(equations)