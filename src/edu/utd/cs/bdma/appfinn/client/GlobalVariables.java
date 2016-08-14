package edu.utd.cs.bdma.appfinn.client;

public class GlobalVariables {

    private boolean isDuplicateDBEntry;

    public boolean isDuplicateDBEntry() {
		return isDuplicateDBEntry;
	}

	public void setDuplicateDBEntry(boolean isDuplicateDBEntry) {
		this.isDuplicateDBEntry = isDuplicateDBEntry;
	}

	public GlobalVariables(boolean isDuplicateDBEntry)
    {
        this.isDuplicateDBEntry = isDuplicateDBEntry;
    }

    public void modify()
    {
    	this.isDuplicateDBEntry = true;
    }
}
