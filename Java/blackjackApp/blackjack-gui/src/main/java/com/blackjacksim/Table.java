package com.blackjacksim;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Table{  
    private Deck shoe;
    private Deck discardTray;
    private Hand tableCards;
    private Dealer dealer;
    private Player player;
    private int numberOfDecks;
    private double shuffleAt;
    private double previousBankroll;
    
    // Game Rules
    private boolean dealerHitsSoft17;
    private boolean doubleAfterSplitAllowed;
    private boolean resplitAcesAllowed;
    private boolean lateSurrenderAllowed;
    private boolean playerCanHitSplitAces;
    private boolean playerCanDoubleSplitAces;

    // Bet spread variables
    private int minBet = 25;
    private int tc1Bet = 100;
    private int tc2Bet = 500;
    private int tc3Bet = 1000;
    private int tc4Bet = 2000;

    public double startingBankroll;
    public double endingBankroll;
    public int totalHands;
    public ArrayList<Integer> betHistory = new ArrayList<>();
    public ArrayList<Double> profitHistory = new ArrayList<>();
    public ArrayList<Double> bankrollHistory = new ArrayList<>();

    // Add the progress callback interface
    @FunctionalInterface
    public interface ProgressCallback {
        void updateProgress(int currentShoe, int totalShoes);
    }

    public Table(int x){
        numberOfDecks = x;
        shoe = new Deck(x);
        discardTray = new Deck(0);
        tableCards = new Hand();
        player = new Player();
        dealer = new Dealer();
    }

    public void setShuffleAt(double d){
        shuffleAt = d;
    }

    public void setGameRules(
        boolean dealerHitsSoft17, 
        boolean doubleAfterSplitAllowed, 
        boolean resplitAcesAllowed, 
        boolean lateSurrenderAllowed, 
        boolean playerCanHitSplitAces, 
        boolean playerCanDoubleSplitAces
    ){
        this.dealerHitsSoft17 = dealerHitsSoft17;
        this.doubleAfterSplitAllowed = doubleAfterSplitAllowed;
        this.resplitAcesAllowed = resplitAcesAllowed;
        this.lateSurrenderAllowed = lateSurrenderAllowed;
        this.playerCanHitSplitAces = playerCanHitSplitAces;
        this.playerCanDoubleSplitAces = playerCanDoubleSplitAces;
    }

    public void setBetSpread(int min, int tc1, int tc2, int tc3, int tc4){
        this.minBet = min;
        this.tc1Bet = tc1;
        this.tc2Bet = tc2;
        this.tc3Bet = tc3;
        this.tc4Bet = tc4;
    }

    public void setPlayerBankroll(double d){
        player.setBankroll(d);
    }

    public void shuffleCards(){
        shoe = new Deck(numberOfDecks);
        discardTray = new Deck(0);
    }

    public double getBankroll(){
        return player.getBankroll();
    }

    public int getRunningCount(){
        int runningCount = tableCards.getTableCount() + discardTray.getRunningCount();
        return runningCount;
    }

    public double getTrueCount(){
        int runningCount = getRunningCount();
        double remainingDecks = shoe.getNumberOfDecks();
        double trueCount = runningCount/remainingDecks;
        trueCount = trueCount*100;
        trueCount = Math.round(trueCount);
        trueCount = trueCount/100;
        return trueCount;
    }

    public void discardCards(){
        for (int i=0; i<player.getNumberOfHands(); i++){
            Hand tempHand = player.getHand(i);
            for (int j=0; j<tempHand.getSize(); j++){
                Card tempCard = tempHand.getCard(j);
                discardTray.addCard(tempCard);
            }
        }
        Hand dealerHand = dealer.getHand();
        for (int i=0; i<dealerHand.getSize(); i++){
            Card tempCard = dealerHand.getCard(i);
            discardTray.addCard(tempCard);
        }
        player.clearHand();
        dealer.clearHand();
        tableCards.clearCards();
    }

    public void dealStartingCards(){
        player.addHand();
        Hand playerHand = player.getHand(0);
        Hand dealerHand = dealer.getHand();
        for (int i=0; i<4; i++){
            int shoeSize = shoe.getShoeSize();
            if (shoeSize == 0) return;
            int randInt = (int) (Math.random()*shoeSize);
            Card tempCard = shoe.getCard(randInt);
            shoe.removeCard(randInt);
            if(i%2==0){
                playerHand.addCard(tempCard);
            }
            else{
                dealerHand.addCard(tempCard);
            }
            tableCards.addCard(tempCard);
        }
        player.setHand(playerHand, 0);
        dealer.setHand(dealerHand);
    }

    public void dealNextCard(int i){
        Hand playerHand = player.getHand(i);
        int shoeSize = shoe.getShoeSize();
        if (shoeSize == 0) return;
        int randInt = (int) (Math.random()*shoeSize);
        Card tempCard = shoe.getCard(randInt);
        shoe.removeCard(randInt);
        playerHand.addCard(tempCard);
        tableCards.addCard(tempCard);
        player.setHand(playerHand, i);
    }

    public int getBetSize(){
        int betSize;
        double trueCount = getTrueCount();
        if (trueCount<1){ 
            betSize = minBet;
        }
        else if(trueCount<2){
            betSize = tc1Bet;
        }
        else if(trueCount<3){
            betSize = tc2Bet;
        }
        else if (trueCount<4){
            betSize = tc3Bet;
        }
        else{
            betSize = tc4Bet;
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

        if (!dealerHitsSoft17){ //Dealer stands on soft 17
            if (playerHand.getCard(0).getSymbol().equals("A")){
                if (player.getNumberOfHands()>1){
                    return resplitAcesAllowed;
                }
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
                else if (doubleAfterSplitAllowed){
                    return true;
                }
                return "4567".contains(dealerUpCardValue);

            }
            else if (playerHand.getCard(0).getSymbol().equals("4")){
                if (dealerUpCardValue.equals("5") || dealerUpCardValue.equals("6")){
                    if(doubleAfterSplitAllowed){
                        return true;
                    }
                }
                return false;

            }
            else if (playerHand.getCard(0).getSymbol().equals("5")){
                return false;
            }
            else if (playerHand.getCard(0).getSymbol().equals("6")){
                if (doubleAfterSplitAllowed){
                    return "23456".contains(dealerUpCardValue);
                }
                else{
                    return "3456".contains(dealerUpCardValue);
                }
                
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
                return false;
            }
        }

        else{ //Dealer hits on soft 17
            if (playerHand.getCard(0).getSymbol().equals("A")){
                if (player.getNumberOfHands()>1){
                    return resplitAcesAllowed;
                }
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
                if (doubleAfterSplitAllowed){
                    return "234567".contains(dealerUpCardValue);
                }
                return "4567".contains(dealerUpCardValue);
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
                return false;
            }
        }
        
    }

    public boolean canPlayerDouble(int i){
        Hand playerHand = player.getHand(i);
        if (playerHand.getSize()!=2){
            return false;
        }
        if(playerHand.isSoftTotal()){
            if (playerCanDoubleSplitAces){
                return true;
            }
            return player.getNumberOfHands()==1;
        }
        return true;
    }

    public boolean doesPlayerDouble(int i){
        Card dealerUpCard = dealer.getUpCard();
        String dealerUpCardSymbol = dealerUpCard.getSymbol();
        Hand playerHand = player.getHand(i);
        boolean canPlayerDouble = canPlayerDouble(i);
        boolean isSoftTotal = playerHand.isSoftTotal();
        int handTotal = playerHand.totalHand();
        double trueCount = getTrueCount();
        
        if(!canPlayerDouble){
            return false;
        }
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
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }

    public void doubleHand(int i)throws IOException{
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
                return false;
            }
        }
    }

    public void dealerTakesCard(){
        Hand dealerHand = dealer.getHand();
        int shoeSize = shoe.getShoeSize();
        int randInt = (int) (Math.random()*shoeSize);
        Card tempCard = shoe.getCard(randInt);
        shoe.removeCard(randInt);
        dealerHand.addCard(tempCard);
        tableCards.addCard(tempCard);
        dealer.setHand(dealerHand);
    }

    public void dealerPlaysHand()throws IOException{
        Hand dealerHand = dealer.getHand();
        if (dealerHand.getSize()!=2){
        }
        int dealerTotal = dealerHand.totalHand();
        while(true){
            if(dealerTotal<17){
                dealerHand = dealer.getHand();
                dealerTakesCard();
                dealerHand = dealer.getHand();
                dealerTotal = dealerHand.totalHand();
            }
            else{
                if (dealerTotal==17 && dealerHand.isSoftTotal() && dealerHitsSoft17){
                    dealerHand = dealer.getHand();
                    dealerTakesCard();
                    dealerHand = dealer.getHand();
                    dealerTotal = dealerHand.totalHand();
                }
                else{
                    break;
                }
            }
        }
    }

    public void winCheck(){
        Hand dealerHand = dealer.getHand();
        Hand playerHand;
        int playerTotal;
        int dealerTotal = dealerHand.totalHand();
        for (int i=0; i<player.getNumberOfHands(); i++){
            playerHand = player.getHand(i);
            playerTotal = playerHand.totalHand(); 
            if(playerTotal>21){
                playerHand.setResult("loss");
            }
            else if(dealerTotal>21 && playerTotal<=21){
                playerHand.setResult("win");
            }
            else if(playerTotal>dealerTotal && playerTotal<=21){
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

    public void payPlayer()throws IOException{
        int betSize = player.getBetSize();
        double bankroll = player.getBankroll();
        Hand playerHand;
        String result;
        for (int i=0; i<player.getNumberOfHands(); i++){
            playerHand = player.getHand(i);
            result = playerHand.getResult();
            if (result.equals("win")){
                if (playerHand.getBetWasDoubled()){
                    bankroll = player.getBankroll();
                    bankroll = bankroll + (2*betSize);
                    player.setBankroll(bankroll);
                }
                else{
                    bankroll = player.getBankroll();
                    bankroll = bankroll + betSize;
                    player.setBankroll(bankroll);
                } 
            }
            else if(result.equals("loss")){
                if (playerHand.getBetWasDoubled()){
                    bankroll = player.getBankroll();
                    bankroll = bankroll-(2*betSize);
                    player.setBankroll(bankroll);
                }
                else{
                    bankroll = player.getBankroll();
                    bankroll = bankroll-betSize;
                    player.setBankroll(bankroll);
                }
            }
            else{
                bankroll = player.getBankroll();
                player.setBankroll(bankroll);
            }
        }
    }

    public void playHand(int i)throws IOException{
        Hand playerHand = player.getHand(i);
        boolean doesPlayerSplit;
        boolean doesPlayerDouble;
        boolean doesPlayerHit;
        
        while(playerHand.getHandIsInPlay()){
            while (true){
                if (player.handCanSplit(i)){
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

    private FileWriter writer; // Add instance variable for file writer

    public void writeToFile() throws IOException {
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
        writer.write(line); // Use instance variable, don't close here

        betHistory.add(player.getBetSize());
        double current = player.getBankroll();
        double profitThisHand = current - previousBankroll;
        double currentBankroll = player.getBankroll();
        bankrollHistory.add(currentBankroll);
        profitHistory.add(profitThisHand);

        previousBankroll = current;
    }

    private void clearFile() throws IOException {
        // Close existing writer if open
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                // Ignore close errors
            }
        }
        // Open new writer for this simulation
        writer = new FileWriter("blackjackResults.csv", false);
    }

    // New version with callback
    public void playShoe(int numberOfShoesToPlay, ProgressCallback callback) throws IOException{
        double bankroll = player.getBankroll();
        int betSize;
        boolean dealerShowsAce;
        boolean playerHasBlackjack;
        boolean dealerHasBlackjack;
        boolean playerTakesInsurance;
        boolean playerTakesEvenMoney;
        int shoesPlayed = 0;

        clearFile();

        startingBankroll = player.getBankroll();
        previousBankroll = startingBankroll;

        for(int i=0; i<numberOfShoesToPlay; i++){
            shuffleCards();
            shoesPlayed++;
            
            // Update progress every 1000 shoes (or adjust as needed)
            if (callback != null && (shoesPlayed % 1000 == 0 || shoesPlayed == numberOfShoesToPlay)) {
                callback.updateProgress(shoesPlayed, numberOfShoesToPlay);
                // Yield to allow UI thread to update
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            
            if (shoesPlayed%1000==0){
                System.out.println("Shoe Number: " + shoesPlayed);
            }
            
            while(shoe.getNumberOfDecks()>shuffleAt){
                writeToFile();                
                player.addHandNumber(); 
                discardCards();

                betSize = getBetSize();

                dealStartingCards();

                playerHasBlackjack = player.blackjackCheck();
                dealerShowsAce = dealerShowsAce();

                if (dealerShowsAce){  
                    dealerHasBlackjack = dealer.blackjackCheck();
                    playerTakesInsurance = playerTakesInsurance();

                    if(playerTakesInsurance && !dealerHasBlackjack){
                        double loss = (betSize*0.5);
                        bankroll = player.getBankroll();
                        bankroll = (bankroll-loss);
                        player.setBankroll(bankroll);
                    }

                    if (playerTakesInsurance && dealerHasBlackjack){
                        continue;                        
                    }

                    playerTakesEvenMoney = playerTakesEvenMoney();

                    if(playerTakesEvenMoney){
                        bankroll = player.getBankroll();
                        bankroll = bankroll + betSize;
                        player.setBankroll(bankroll);
                        continue;
                    }
                }

                if (playerHasBlackjack){
                    double payout = (betSize*1.5);
                    bankroll = player.getBankroll();
                    bankroll = bankroll + payout;
                    player.setBankroll(bankroll);
                    continue;
                }
                else{
                    int handNumber=0;
                    while(playerHasHandInPlay()){
                        playHand(handNumber);
                        handNumber++;
                    }
                    dealerPlaysHand();
                    winCheck();
                    payPlayer();
                }
            }
        }
        
        // Final progress update
        if (callback != null) {
            callback.updateProgress(numberOfShoesToPlay, numberOfShoesToPlay);
        }
        
        // Close the file writer
        if (writer != null) {
            writer.close();
            writer = null;
        }
        
        endingBankroll = player.getBankroll();
        totalHands = player.getHandNumber();
    }

    // Keep old method for backward compatibility
    public void playShoe(int numberOfShoesToPlay) throws IOException {
        playShoe(numberOfShoesToPlay, null);
    }
}