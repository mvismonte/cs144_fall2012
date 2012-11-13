package edu.ucla.cs.cs144;

public class SearchResult {
	private String itemId;
	private String name;
    
	public SearchResult() {}
    
	public SearchResult(String itemId, String name) {
		this.itemId = itemId;
		this.name = name;
	}
    
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	// Add hashCode function for use in HashSet.
  public int hashCode() {
  	return this.itemId.hashCode();
  }

  @Override
  // Add hashCode function for use in HashSet.
  public boolean equals(Object o) {
  	return this.name.equals(((SearchResult) o).getName());
  }
}
