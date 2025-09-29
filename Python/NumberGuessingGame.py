import random
number = random.randint(1,100)
guesses = 0
while True:
    
    guess = int(input('Guess the number: '))
    if guess == number:
        guesses = guesses + 1
        print(f'Congrats! You guessed the number in {guesses} guesses!')
        break
    elif guess < number:
        guesses = guesses + 1
        print('Too low. Guess higher.')
    elif guess > number:
        guesses = guesses + 1
        print('Too high. Guess lower.')
    else: print('ERROR, Try again')
    