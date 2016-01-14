package com.unina.ajibika;

public class DrawerItem {
	 
    String ItemName;
    int imgResID;
    int ItemID=0;

    public DrawerItem(String itemName,int itemID,int imgResID) {
          super();
          ItemName = itemName;
          ItemID = itemID;
          this.imgResID = imgResID;
    }

    public String getItemName() {
          return ItemName;
    }
    public void setItemName(String itemName) {
          ItemName = itemName;
    }
    public int getItemID() {
        return ItemID;
    }
    public void setItemID(int itemId) {
    	ItemID = itemId;
    }
    public int getImgResID() {
          return imgResID;
    }
    public void setImgResID(int imgResID) {
          this.imgResID = imgResID;
    }

}
