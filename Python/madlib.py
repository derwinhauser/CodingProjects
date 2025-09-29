

def user_inputs():
    def adjectives():
        adjectives = []
        for i in range(0,8):
            adjectives.append(input('Adjective: '))
        return adjectives

    def nouns():
        nouns = []
        for i in range(0,5):
            nouns.append(input('Noun: '))
        return nouns

    def colors():
        colors = []
        for i in range(0,2):
            colors.append(input('Color: '))
        return colors

    def emotions():
        emotions = []
        for i in range(0,1):
            emotions.append(input('Emotion: '))
        return emotions

    def verbs():
        verbs = []
        for i in range(0,2):
            verbs.append(input('Verb: '))
        return verbs

    def plural_nouns():
        plural_nouns = []
        for i in range(0,5):
            plural_nouns.append(input('Plural Nouns: '))
        return plural_nouns

    def shapes():
        shapes = []
        for i in range(0,1):
            shapes.append(input('Shapes: '))
        return shapes
    adjectives = adjectives()
    nouns = nouns()
    colors = colors()
    emotions =emotions()
    verbs = verbs()
    plural_nouns = plural_nouns()
    shapes = shapes()
    return adjectives, colors, nouns, emotions, verbs, plural_nouns, shapes

def main():
    x = user_inputs()
    print(f'One sunny {adjectives[0]} afternoon, the big day finally arrived! The {adjective[1]} couple, dressed in COLOR and COLOR, stood at the end of the NOUN as their family and friends cheered. The ceremony was filled with EMOTION, as the officiant declared, “I now pronounce you NOUN and NOUN!” The couple sealed the deal with a {adjectives[2]} kiss that made everyone VERB in delight. At the reception, the {adjectives[3]} cake stole the show. It was shaped like a NOUN and topped with PLURAL NOUN. The guests were so impressed that they yelled out "{adjectives[4]} NOUN!". The highlight of the evening was the first dance. The couple twirled like PLURAL NOUN on the dance floor. Everyone joined in, forming a SHAPE around the happy pair. By the end of the night, even the {adjectives[5]} guests were up and VERB-ING! It was a {adjectives[6]} day full of PLURAL NOUN, laughter, and PLURAL NOUN. As the couple left for their {adjectives[7]} honeymoon, everyone waved their PLURAL NOUN.')
main()