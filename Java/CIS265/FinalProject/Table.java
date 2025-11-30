import java.util.ArrayList;

public class Table{  
    private Deck shoe;
    private Deck discardTray;
    private Hand tableCards;
    private Dealer dealer;
    private Player player;
    
    public Table(){
        shoe = new Deck(4);
        discardTray = new Deck(0);
        tableCards = new Hand();
        player = new Player();
        dealer = new Dealer();
    }

    public double getBankroll(){
        double bankroll = player.getBankroll();
        return bankroll;
    }

    public int getRunningCount(){
        int runningCount = tableCards.getTableCount() + discardTray.getRunningCount();
        return runningCount;
    }

    public double getTrueCount(){
        int runningCount = getRunningCount();
        double remainingDecks = shoe.getNumberOfDecks();
        double trueCount = runningCount/remainingDecks;
        // round to two decimals
        trueCount = trueCount*100;
        trueCount = Math.round(trueCount);
        trueCount = trueCount/100;
        return trueCount;
    }

    public void discardCards(){
        // add player hands to discard tray
        for (int i=0; i<player.getNumberOfHands(); i++){
            Hand tempHand = player.getHand(i);
            for (int j=0; j<tempHand.getSize(); j++){
                Card tempCard = tempHand.getCard(j);
                discardTray.addCard(tempCard);
            }
        }
        // add dealer hands to discard tray
        for (int i=0; i<dealer.getHandSize(); i++){
            Hand tempHand = dealer.getHand();
            Card tempCard = tempHand.getCard(i);
            discardTray.addCard(tempCard);
        }
        // clear hands from table
        player.clearHand();
        dealer.clearHand();
        tableCards.clearCards();
    }

    public void dealStartingCards(){
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        for (int i=0; i<4; i++){
            // variables
            int shoeSize = shoe.getShoeSize();
            int randInt = (int) (Math.random()*shoeSize);

            // get card from shoe
            Card tempCard = shoe.getCard(randInt);
            
            // remove card from shoe
            shoe.removeCard(randInt);
            
            // add removed card to player or dealer hand
            if(i%2==0){
                playerHand.addCard(tempCard);
            }
            else{
                dealerHand.addCard(tempCard);
            }
            
            //add cards to tableCards variable to track running count without interference from dealer's down card
            tableCards.addCard(tempCard);
        }
        player.setHand(playerHand);
        dealer.setHand(dealerHand);
    }

    public int getBetSize(){
        int betSize = 0;
        double trueCount = getTrueCount();
        if (trueCount<1){
            betSize = 3;
        }
        else if(trueCount<2){
            betSize = 10;
        }
        else if(trueCount<3){
            betSize = 15;
        }
        else if (trueCount<4){
            betSize = 25;
        }
        else{
            betSize = 50;
        }
        return betSize;
    }

    public boolean dealerShowsAce(){
        Hand dealerHand = dealer.getHand();
        if(dealerHand.dealerUpCard().equals("A")){
            return true;
            }
        else{
            return false;
        }
    }

    public boolean playerTakesInsurance(){
        boolean dealerShowsAce = dealerShowsAce();
        boolean playerHasBlackjack = player.blackjackCheck();
        double trueCount = getTrueCount();
        if (dealerShowsAce && !playerHasBlackjack){
            if (trueCount>=3){
            return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
        
    }

    public boolean playerTakesEvenMoney(){
        boolean dealerShowsAce = dealerShowsAce();
        boolean playerHasBlackjack = player.blackjackCheck();
        double trueCount = getTrueCount();
        if (dealerShowsAce && playerHasBlackjack){
            if (trueCount>=3){
            return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void printTable(){
        //print bankroll
        double bankroll = player.getBankroll();
        System.out.println("Bankroll: " + bankroll);
        
        //print remaining decks
        double remainingDecks = shoe.getNumberOfDecks();
        System.out.println("Remaining Decks: " + remainingDecks);

        //print counts
        int runningCount = getRunningCount();
        System.out.println("Running Count: " + runningCount);
        double trueCount = getTrueCount();
        System.out.println("True Count: " + trueCount);

        //print betSize
        int betSize = getBetSize();
        System.out.println("Bet Size: " + betSize);

        Hand dealerHand = dealer.getHand();
        //print dealer hand
        System.out.print("Dealer: ");
        dealerHand.printDealerHand();
        Card dealerUpCard = dealer.getUpCard();
        System.out.println("Dealer UpCard: " + dealerUpCard.getSymbol());

        //reveal dealer cards for debug
        System.out.print("dealer revealed: ");
        dealerHand.printHand();
        System.out.println();

        //print player hands
        int numberOfHands = player.getNumberOfHands();
        for (int i=0; i<numberOfHands; i++){
            Hand playerHand = player.getHand(i);
            int playerTotal = playerHand.totalHand();
            System.out.print("Player: ");
            playerHand.printHand();
            System.out.println();
            System.out.println("player Total: " + playerTotal);
        }
        System.out.println();
    }

    public boolean playerCanSplit(){
        if (player.getNumberOfHands()>3){
            return false;
        }
        else{
            for(int i=0; i<player.getNumberOfHands(); i++){
                Hand tempHand = player.getHand(i);
                if (tempHand.getSize()==2){
                    if (tempHand.getCard(0).getCardValue()==tempHand.getCard(1).getCardValue()){
                        return true;
                    }
                    else{
                        continue;
                    }
                }
                else{
                    continue;
                }
            }
            return false;
        }
    }

    public boolean playerHasHandInPlay(){
        for(int i=0; i<player.getNumberOfHands(); i++){
            Hand tempHand = player.getHand(i);
            if (tempHand.getHandIsInPlay()){
                return true;
            }
        }
        return false;
    }

    public boolean doesPlayerSplit(Hand playerHand){
        Card dealerUpCard = dealer.getUpCard();
        String dealerUpCardSymbol = dealerUpCard.getSymbol();
        String dealerUpCardValue = dealerUpCard.getCardValue();
        double trueCount = getTrueCount();
        if (playerHand.getCard(0).getSymbol().equals("A")){
            return true;
        }
        else if (playerHand.getCard(0).getCardValue().equals("10")){
            if (dealerUpCardSymbol.equals("6")){
                if (trueCount>=4){
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (dealerUpCardSymbol.equals("5")){
                if (trueCount>=5){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (dealerUpCardValue.equals("4")){
                if (trueCount>=6){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else if (playerHand.getCard(0).getSymbol().equals("2") || playerHand.getCard(0).getSymbol().equals("3")){
            if (dealerUpCardValue.equals("8") || dealerUpCardValue.equals("9") || dealerUpCardValue.equals("10") || dealerUpCardSymbol.equals("A")){
                return false;
            }
            else{
                return true;
            }
        }
        else if (playerHand.getCard(0).getSymbol().equals("4")){
            if (dealerUpCardValue.equals("5") || dealerUpCardValue.equals("6")){
                return true;
            }
            else{
                return false;
            }
        }
        else if (playerHand.getCard(0).getSymbol().equals("5")){
            return false;
        }
        else if (playerHand.getCard(0).getSymbol().equals("6")){
            return "23456".contains(dealerUpCardValue);

        }
        else if (playerHand.getCard(0).getSymbol().equals("7")){
            return "234567".contains(dealerUpCardValue);
        }
        else if (playerHand.getCard(0).getSymbol().equals("8")){
            return true;
        }
        else if (playerHand.getCard(0).getSymbol().equals("9")){
            if (dealerUpCardValue.equals("7") || dealerUpCardValue.equals("10") || dealerUpCardSymbol.equals("A")){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            System.out.println("-----------********ERROR IN DOESPLAYERSPLIT FUNCTION IN TABLE>JAVA" );
            return false;
        }
    }

    public void playHand(int i){
        Hand playerHand = player.getHand(i);
        Card dealerUpCard = dealer.getUpCard();
        if (playerHand.handCanSplit()){
            boolean doesPlayerSplit = doesPlayerSplit(playerHand);
        }
    }

    public void playShoe(){
        // variables
        double bankroll = player.getBankroll();
        int betSize;
        boolean dealerShowsAce;
        boolean playerHasBlackjack;
        boolean dealerHasBlackjack;
        boolean playerTakesInsurance;
        boolean playerTakesEvenMoney;
        boolean playerHasHandInPlay;

        while(shoe.getNumberOfDecks()>1.5){
            discardCards();
            betSize = getBetSize();
            dealStartingCards();
            playerHasHandInPlay = true;
            printTable(); //debug
            playerHasBlackjack = player.blackjackCheck();
            dealerShowsAce = dealerShowsAce();
            if (dealerShowsAce){  
                dealerHasBlackjack = dealer.blackjackCheck();
                playerTakesInsurance = playerTakesInsurance();
                
                if(playerTakesInsurance && !dealerHasBlackjack){
                    bankroll = bankroll-(0.5*betSize);
                }
                
                if (playerTakesInsurance && dealerHasBlackjack){
                    continue;
                }
                
                playerTakesEvenMoney = playerTakesEvenMoney();
                
                if(playerTakesEvenMoney){
                    bankroll = bankroll + betSize;
                    continue;
                }
            }
            else{
                if (playerHasBlackjack){
                    double payout = betSize*1.5;
                    bankroll = bankroll + payout;
                    continue;
                }
                else{
                    while(playerHasHandInPlay){
                        int i=0;
                        playHand(i);

                        i++;
                        playerHasHandInPlay = playerHasHandInPlay();
                    }

                }
            }
        }
    }
}