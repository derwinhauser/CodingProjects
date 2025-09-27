import random as r

prime = []
not_prime = []

equations = []
max_int = 100

# spearates prime and non-prime numbers
for i in range(2, max_int): 
    tracker = 1
    for y in range(2, i):
        tracker = tracker*(i%y)
    if tracker == 0:
        not_prime.append(i)
    else: prime.append(i)

for int in range(4, max_int, 2):
    for number in prime:
        ans = int - number
        if ans in prime:
            equations.append([int, ans, number])
        else: continue

    
for even in range(4, max_int, 2):
    print(f'{even}: ', end="")
    for equation in equations:       
        if even == equation[0]:
            print(f'{equation[1]} + {equation[2]}', end='|')
    print('')