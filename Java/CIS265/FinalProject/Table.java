import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Table{  
    private Deck shoe;
    private Deck discardTray;
    private Hand tableCards;
    private Dealer dealer;
    private Player player;
    private int numberOfDecks;
    
    public Table(int x){
        numberOfDecks = x;
        shoe = new Deck(x);
        discardTray = new Deck(0);
        tableCards = new Hand();
        player = new Player();
        dealer = new Dealer();
    }

    public void shuffleCards(){
        shoe = new Deck(numberOfDecks);
        discardTray = new Deck(0);
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
        player.addHand();
        Hand playerHand = player.getHand(0);
        Hand dealerHand = dealer.getHand();
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
        player.setHand(playerHand, 0);
        dealer.setHand(dealerHand);
    }

    public void dealNextCard(int i){
        Hand playerHand = player.getHand(i);
        int shoeSize = shoe.getShoeSize();
        int randInt = (int) (Math.random()*shoeSize);
        Card tempCard = shoe.getCard(randInt);
        //remove card from shoe
        shoe.removeCard(randInt);
        //add card to player's hand
        playerHand.addCard(tempCard);
        //add cards to tableCards variable to track running count without interference from dealer's down card
        tableCards.addCard(tempCard);
        //update player hand
        player.setHand(playerHand, i);
    }

    public int getBetSize(){
        int betSize = 0;
        double trueCount = getTrueCount();
        if (trueCount<1){
            betSize = 5;
        }
        else if(trueCount<2){
            betSize = 25;
        }
        else if(trueCount<3){
            betSize = 50;
        }
        else if (trueCount<4){
            betSize = 50;
        }
        else{
            betSize = 50;
        }
        player.setBetSize(betSize);
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

    public boolean dealerHasHandInPlay(){
        Hand dealerHand = dealer.getHand();
        if (dealerHand.getHandIsInPlay()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean doesPlayerSplit(int i){
        Hand playerHand = player.getHand(i);
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
            System.out.println("-----------********ERROR IN DOESPLAYERSPLIT FUNCTION IN TABLE.JAVA" );
            return false;
        }
    }

    public boolean canPlayerDouble(int i){
        Hand playerHand = player.getHand(i);
        if (playerHand.getSize()==2){
            if(playerHand.isSoftTotal()){
                if (player.getNumberOfHands()==1){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean doesPlayerDouble(int i){
        Card dealerUpCard = dealer.getUpCard();
        String dealerUpCardSymbol = dealerUpCard.getSymbol();
        Hand playerHand = player.getHand(i);
        boolean canPlayerDouble = canPlayerDouble(i);
        boolean isSoftTotal = playerHand.isSoftTotal();
        int handTotal = playerHand.totalHand();
        double trueCount = getTrueCount();
        
        if(canPlayerDouble){
            if (isSoftTotal){
                if (handTotal>=19){
                    return false;
                }
                else if (handTotal==18){
                    if("23456".contains(dealerUpCardSymbol)){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else if(handTotal==17){
                    if("3456".contains(dealerUpCardSymbol)){
                        return true;
                    }
                    else if(dealerUpCardSymbol.equals("2")){
                        if (trueCount>=1){
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
                else if(handTotal==16 || handTotal==15){
                    return "456".contains(dealerUpCardSymbol);
                }
                else if(handTotal==14 || handTotal==13){
                    return "56".contains(dealerUpCardSymbol);
                }
                else{
                    System.out.println("ERROR EITH DOESPLAYERDOUBLE FUNCTION IN TABLE.JAVA");
                    return false;
                }
            }
            else{
                if(handTotal>11){
                    return false;
                }
                else if(handTotal==11){
                    if (dealerUpCardSymbol.equals("A")){
                        if (trueCount>=1){
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        return true;
                    }
                }
                else if(handTotal==10){
                    return "23456789".contains(dealerUpCardSymbol);
                }
                else if(handTotal==9){
                    if(dealerUpCardSymbol.equals("2")){
                        if (trueCount>=1){
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        return "3456".contains(dealerUpCardSymbol);
                    }
                }
                else if(handTotal<9){
                    return false;
                }
                else{
                    System.out.println("ERROR IN DOESPLAYERDOUBLE FUNCTION IN TABLE.JAVA");
                    return false;
                }
            }
        }
        else{
            return false;
        }
        
    }

    public void doubleHand(int i){
        dealNextCard(i);
        Hand playerHand = player.getHand(i);
        playerHand.setBetWasDoubled(true);
        playerHand.setHandIsInPlay(false);
        player.setHand(playerHand, i);

    }

    public boolean doesPlayerHit(int i){
        Hand playerHand = player.getHand(i);
        int playerTotal = playerHand.totalHand();
        Card dealerUpCard = dealer.getUpCard();
        String dealerUpCardSymbol = dealerUpCard.getSymbol();
        boolean isSoftTotal = playerHand.isSoftTotal();
        double trueCount = getTrueCount();
        
        if(isSoftTotal){
            if (playerTotal>=19){
                return false;
            }
            else if (playerTotal<=17){
                return true;
            }
            else if(playerTotal == 18){
                if("2345678".contains(dealerUpCardSymbol)){
                    return false;
                }
                else{
                    return true;
                }
                
            }
            else{
                System.out.println("ERROR WITH DOESPLAYERHIT FUNCTION IN TABLE.JAVA");
                return false;
            }
        }
        else{
            if(playerTotal>=17){
                return false;
            }
            else if(playerTotal>=13){
                if("23456".contains(dealerUpCardSymbol)){
                    return false;
                }
                else{
                    return true;
                }
            }
            else if (playerTotal==12){
                if ("456".contains(dealerUpCardSymbol)){
                    return false;
                }
                else{
                    return true;
                }
            }

            else if(playerTotal<=11){
                return true;
            }
            else{
                System.out.println("ERROR IN DOESPLAYERHIT FUNCTION IN TABLE.JAVA");
                return false;
            }
            
        }

    }

    public void dealerTakesCard(){
        Hand dealerHand = dealer.getHand();
        int shoeSize = shoe.getShoeSize();
        int randInt = (int) (Math.random()*shoeSize);
        Card tempCard = shoe.getCard(randInt);
        //remove card from shoe
        shoe.removeCard(randInt);
        //add card to player's hand
        dealerHand.addCard(tempCard);
        //add cards to tableCards variable to track running count without interference from dealer's down card
        tableCards.addCard(tempCard);
        //update player hand
        dealer.setHand(dealerHand);
    }

    public void dealerPlaysHand(){
        Hand dealerHand = dealer.getHand();
        int dealerTotal = dealerHand.totalHand();
        while(dealerTotal<17){
            dealerHand = dealer.getHand();
            dealerTotal = dealerHand.totalHand();
            dealerTakesCard();
        }
    }

    public void winCheck(){
        for (int i=0; i<player.getNumberOfHands(); i++){
            Hand dealerHand = dealer.getHand();
            Hand playerHand = player.getHand(i);
            int playerTotal = playerHand.totalHand();
            int dealerTotal = dealerHand.totalHand();
            if(playerTotal>21){
                playerHand.setResult("loss");
            }
            else if(dealerTotal>21 && playerTotal<=21){
                playerHand.setResult("win");
            }
            else if(playerTotal>dealerTotal){
                playerHand.setResult("win");
            }
            else if(playerTotal<dealerTotal && dealerTotal<=21){
                playerHand.setResult("loss");
            }
            else{
                playerHand.setResult("push");
            }
            player.setHand(playerHand, i);
        }
    }

    public void payPlayer(){
        int betSize = player.getBetSize();
        double bankroll = player.getBankroll();
        for (int i=0; i<player.getNumberOfHands(); i++){
            Hand playerHand = player.getHand(i);
            String result = playerHand.getResult();
            if (result.equals("win")){
                bankroll = bankroll + betSize;
                
            }
            else if(result.equals("loss")){
                bankroll = bankroll-betSize;
            }
            else{
                bankroll = bankroll;
            }
        }
        player.setBankroll(bankroll);
    }

    public void playHand(int i){
        Hand playerHand = player.getHand(i);
        Card dealerUpCard = dealer.getUpCard();
        boolean doesPlayerSplit;
        boolean doesPlayerDouble;
        boolean doesPlayerHit;
        while(playerHand.getHandIsInPlay()){
            while (true){//loop determines splits
                if (playerHand.handCanSplit()){
                    doesPlayerSplit = doesPlayerSplit(i);
                }
                else{
                    doesPlayerSplit = false;
                }
                if(doesPlayerSplit){
                    player.splitHand(i);
                    dealNextCard(i);
                }
                else{
                    break;
                }
                playerHand = player.getHand(i);
            }
            doesPlayerDouble = doesPlayerDouble(i);
            if (doesPlayerDouble){
                doubleHand(i);
            }
            else{
                while (playerHand.getHandIsInPlay()){
                    doesPlayerHit = doesPlayerHit(i);
                    if(doesPlayerHit){
                        dealNextCard(i);
                    }
                    else{
                        playerHand.setHandIsInPlay(false);
                    }
                }
            }
            playerHand = player.getHand(i);
        }
        player.setHand(playerHand, i);
    }

    public void writeToFile() throws IOException {
        FileWriter writer = new FileWriter("blackjackResults.csv", true);
        int handNumber = player.getHandNumber();
        String handNumberString = String.valueOf(handNumber);
        double bankroll = player.getBankroll();
        String bankrollString = String.valueOf(bankroll);
        int betSize = player.getBetSize();
        String betSizeString = String.valueOf(betSize);
        double trueCount = getTrueCount();
        String trueCountString = String.valueOf(trueCount);
        String line = "";
        line = handNumberString+","+trueCountString+","+betSizeString+","+bankrollString+"\n";
        writer.write(line);
        writer.close();
    }

    public void playShoe(int numberOfShoesToPlay) throws IOException{
        // variables
        double bankroll = player.getBankroll();
        int betSize;
        boolean dealerShowsAce;
        boolean playerHasBlackjack;
        boolean dealerHasBlackjack;
        boolean playerTakesInsurance;
        boolean playerTakesEvenMoney;
        boolean playerHasHandInPlay;

        for(int i=0; i<numberOfShoesToPlay;i++){
            shuffleCards();
            while(shoe.getNumberOfDecks()>1.5){
                writeToFile();
                player.addHandNumber();
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
                        player.setBankroll(bankroll);
                    }
                    
                    if (playerTakesInsurance && dealerHasBlackjack){
                        continue;
                    }
                    
                    playerTakesEvenMoney = playerTakesEvenMoney();
                    
                    if(playerTakesEvenMoney){
                        bankroll = bankroll + betSize;
                        player.setBankroll(bankroll);
                        continue;
                    }
                }
                if (playerHasBlackjack){
                    double payout = betSize*1.5;
                    bankroll = bankroll + payout;
                    player.setBankroll(bankroll);
                    continue;
                }
                else{
                    int handNumber=0;
                    while(playerHasHandInPlay){
                        playHand(handNumber);
                        handNumber++;
                        playerHasHandInPlay = playerHasHandInPlay();
                    }
                    dealerPlaysHand();
                    winCheck();
                    payPlayer();
                }
            }
        }
    }
}