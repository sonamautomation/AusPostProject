package DTO;

public class TibcoMessageDetails {

    private String Id ;
    private String BusinessReferenceId ;
    private String Amount;
    private String ProductId;
    private String ReceiptNumber;
    private String Budget;


	public String getBusinessReferenceId() {
		return BusinessReferenceId;
	}

	public void setBusinessReferenceId(String businessReferenceId) {
		BusinessReferenceId = businessReferenceId;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getProductId() {
		return ProductId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getBudget() {
		return Budget;
	}

	public void setBudget(String budget) {
		Budget = budget;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getReceiptNumber() {
		return ReceiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		ReceiptNumber = receiptNumber;
	}

}
