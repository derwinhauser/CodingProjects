import random as r

prime = []
not_prime = []

max_int = 100

for i in range(2, max_int-1):
    tracker = 1
    for y in range(2, i-1):
        tracker = tracker*(i%y)
    if tracker == 0:
        not_prime.append(i)
    else: prime.append(i)

correct = 0
incorrect = 0

while True:

    for rounds in range(10):
        number = r.randint(2, max_int)
        if number in prime:
            isprime = True
        elif number in not_prime:
            isprime = False
        else: print('ERROR')

        while True:
            choice = input(f"Is {number} prime? (y/n) ")
            if choice == 'y':
                choice = True
                break
            elif choice == 'n':
                choice = False
                break
            else: print('ERROR')
        
        if choice == isprime:
            print('CORRECT!')
            correct = correct + 1
        else: 
            print('INCORRECT!')
            incorrect = incorrect + 1
    
    play_again = input('Play again? (y/n)')
    if play_again == 'n':
        break

print(f'You got {correct} guesses right and {incorrect} guesses wrong')