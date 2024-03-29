public class Player 
{
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }
    
    // if longest chain is about 14, it creates winning condition, so check winning returns true
    public boolean checkWinning() 
    {
        return findLongestChain() == 14;
    }
    
    // simple bubble sort for tile value sorting
    public void tileSort() 
    {
        int n = numberOfTiles;
        for (int i = 0; i < n - 1; i++) 
        {
            for (int j = 0; j < n - i - 1; j++) 
            {
                // null case handling for not getting nullpointer exception
                if (playerTiles[j] != null && playerTiles[j + 1] != null && playerTiles[j].getValue() > playerTiles[j + 1].getValue()) 
                {
                    // Swap playerTiles[j] and playerTiles[j+1]
                    Tile temp = playerTiles[j];
                    playerTiles[j] = playerTiles[j + 1];
                    playerTiles[j + 1] = temp;
                }
            }
        }
    }
    
    // it checks tile chain checker, and it returns
    public int findLongestChain() 
    {
        tileSort();
        int count = 1;
        int longestChain = 1;
        for (int i = 1; i < numberOfTiles; i++) 
        {
            // Check if either playerTiles[i] or playerTiles[i-1] is null before accessing getValue()
            if (playerTiles[i] != null && playerTiles[i - 1] != null && playerTiles[i].getValue() == (playerTiles[i - 1].getValue() + 1)) 
            {
                count++;
                if (count > longestChain) 
                {
                    longestChain = count;
                }
            } else if (playerTiles[i].getValue() != playerTiles[i - 1].getValue()){ //If they are the same, keep count but don't increase it
                count = 1;
            }
        }
        return longestChain;
    }
    
    /** Ü essentially the same as findLongestChain method but
     * @return index of the longest chain
     */
    public int indexOfChain(){
        tileSort();
        int count = 1;
        int index = 0;
        for (int i = 1; i < numberOfTiles; i++) 
        {
            // Check if either playerTiles[i] or playerTiles[i-1] is null before accessing getValue()
            if (playerTiles[i] != null && playerTiles[i - 1] != null && playerTiles[i].getValue() == (playerTiles[i - 1].getValue() + 1)) 
            {
                index = (i - 1);
                count++;
                if (count == findLongestChain()) 
                {
                    break;
                }
            } 
            else 
            {
                // count reset
                count = 1;
            }
        }

        return index;
    }

    public Tile getAndRemoveTile(int index) 
    {
        Tile removedTile = playerTiles[index];
        // Shift tiles to fill the gap left by the removed tile
        for (int i = index; i < numberOfTiles - 1; i++) 
        {
            playerTiles[i] = playerTiles[i + 1];
        }
        playerTiles[numberOfTiles - 1] = null;
        numberOfTiles--;
        tileSort();
        return removedTile;
    }

    // addTile method which is about adding one tile to player or computer players deck 
    public void addTile(Tile t) {
        if (numberOfTiles < 15 && t != null) {
            playerTiles[numberOfTiles] = t;
            numberOfTiles++;
            tileSort();
        } else {
            System.out.println("Cannot add more tiles, player's hand is full or the provided tile is null.");
        }
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                return i;
            }
        }
        return -1; // Return -1 if tile not found
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() 
    {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) 
        {
            if (playerTiles[i] != null) 
            {
                System.out.print(playerTiles[i].toString() + " ");
            }
        }
        System.out.println();
    }


    // getter (accessor methods)
    public Tile[] getTiles() {
        return playerTiles;
    }

    public String getName() {
        return playerName;
    }

    public int getDuplicateIndex(){
        for (int i = 1; i < playerTiles.length && playerTiles[i] != null; i++) {
            if (playerTiles[i - 1].getValue() == playerTiles[i].getValue()) {
                return i;
            }
        }
        return -1;
    }

    public int getChainLengthAtIndex(int input){
        int chainLength = 1;

        boolean done = false;
        for (int index = input; !done && index + 1 < numberOfTiles && playerTiles[index+1] != null; index++) {
            if (playerTiles[index].getValue() == playerTiles[index+1].getValue() - 1) {
                chainLength += 1;
            } else if (playerTiles[index].getValue() != playerTiles[index+1].getValue()){
                done = true;
            }
        }

        done = false;
        for (int index = input; !done && index - 1 >= 0 && playerTiles[index] != null; index--) {
            if (playerTiles[index].getValue() == playerTiles[index-1].getValue() + 1) {
                chainLength += 1;
            } else if (playerTiles[index].getValue() != playerTiles[index-1].getValue()){
                done = true;
            }
        }

        return chainLength;
    }

    // setter (mutator methods)
    public void setName(String name) {
        playerName = name;
    }
}

