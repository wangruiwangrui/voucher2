package com.voucher.manage.daoModelJoin.TTT;

import java.io.Serializable;
import java.util.Date;

import com.voucher.manage.daoSQL.annotations.SQLBoolean;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class ChartInfo_roomInfo_Join implements Serializable{
	private static final long serialVersionUID = 1L;

	@SQLString(name = "GUID")
	private String GUID;

	@SQLString(name = "HireGUID")
	private String HireGUID;

	@SQLString(name = "RoomAddress")
	private String RoomAddress;

	@SQLString(name = "Charter")
	private String Charter;

	@SQLString(name = "ChartType")
	private String ChartType;

	@SQLString(name = "CharterProperty")
	private String CharterProperty;

	@SQLString(name = "Sex")
	private String Sex;

	@SQLString(name = "CardType")
	private String CardType;

	@SQLString(name = "IDNo")
	private String IDNo;

	@SQLString(name = "Corporation")
	private String Corporation;

	@SQLString(name = "Phone")
	private String Phone;

	@SQLString(name = "HomeAddress")
	private String HomeAddress;

	@SQLString(name = "ChartProperty")
	private String ChartProperty;

	@SQLString(name = "ContractNo")
	private String ContractNo;

	@SQLDateTime(name = "ConcludeDate")
	private Date ConcludeDate;

	@SQLDateTime(name = "ChartBeginDate")
	private Date ChartBeginDate;

	@SQLDateTime(name = "ChartEndDate")
	private Date ChartEndDate;

	@SQLFloat(name = "AssureAmount")
	private Float AssureAmount;

	@SQLFloat(name = "Hire")
	private Float Hire;

	@SQLFloat(name = "ChartCriterion")
	private Float ChartCriterion;

	@SQLFloat(name = "Area")
	private Float Area;

	@SQLFloat(name = "TotalHire")
	private Float TotalHire;

	@SQLFloat(name = "AddRate")
	private Float AddRate;

	@SQLString(name = "AddType")
	private String AddType;

	@SQLString(name = "BeginAddDate")
	private String BeginAddDate;

	@SQLDateTime(name = "HireEndDate")
	private Date HireEndDate;

	@SQLFloat(name = "AccountBalance")
	private Float AccountBalance;

	@SQLString(name = "OweHireResion")
	private String OweHireResion;

	@SQLString(name = "CreditRating")
	private String CreditRating;

	@SQLString(name = "FareItem")
	private String FareItem;

	@SQLString(name = "ShopName")
	private String ShopName;

	@SQLString(name = "LowProtect")
	private String LowProtect;

	@SQLString(name = "CharterCode")
	private String CharterCode;

	@SQLInteger(name = "CalculateHireDay")
	private Integer CalculateHireDay;

	@SQLString(name = "Memo")
	private String Memo;

	@SQLBoolean(name = "IsHistory")
	private Boolean IsHistory;

	@SQLString(name = "BelongUnit")
	private String BelongUnit;

	@SQLString(name = "Operator")
	private String Operator;

	@SQLInteger(name = "AgeLimite")
	private Integer AgeLimite;

	@SQLBoolean(name = "IsStopHireIn")
	private Boolean IsStopHireIn;

	@SQLString(name = "StopHireInResion")
	private String StopHireInResion;

	@SQLString(name = "DangerClassification")
	private String DangerClassification;

	@SQLString(name = "NSRSBH")
	private String NSRSBH;

	@SQLString(name = "KHH")
	private String KHH;

	@SQLString(name = "AddFashion")
	private String AddFashion;

	@SQLFloat(name = "PlacementFees")
	private Float PlacementFees;

	@SQLFloat(name = "GasInstallationFees")
	private Float GasInstallationFees;

	@SQLString(name = "Ein")
	private String Ein;

	@SQLString(name = "company")
	private String company;

	@SQLInteger(name = "HireWXType")
	private Integer HireWXType;

	@SQLString(name = "Num")
	private String Num;

	@SQLString(name = "OriginalNum")
	private String OriginalNum;

	@SQLString(name = "Address")
	private String Address;

	@SQLString(name = "OriginalAddress")
	private String OriginalAddress;

	@SQLString(name = "Region")
	private String Region;

	@SQLString(name = "Segment")
	private String Segment;

	@SQLString(name = "ManageRegion")
	private String ManageRegion;

	@SQLString(name = "RoomProperty")
	private String RoomProperty;

	@SQLString(name = "Useful")
	private String Useful;

	@SQLString(name = "Floor")
	private String Floor;

	@SQLString(name = "State")
	private String State;

	@SQLString(name = "Structure")
	private String Structure;

	@SQLFloat(name = "BuildArea")
	private Float BuildArea;

	@SQLString(name = "RoomType")
	private String RoomType;

	@SQLString(name = "IsCity")
	private String IsCity;

	@SQLString(name = "Manager")
	private String Manager;

	@SQLString(name = "ManagerPhone")
	private String ManagerPhone;

	@SQLString(name = "IsStreet")
	private String IsStreet;

	@SQLString(name = "FitMent")
	private String FitMent;

	@SQLString(name = "BeFrom")
	private String BeFrom;

	@SQLDateTime(name = "InDate")
	private Date InDate;

	@SQLString(name = "PropertyRightNo")
	private String PropertyRightNo;

	@SQLFloat(name = "PropertyRightArea")
	private Float PropertyRightArea;

	@SQLString(name = "DesignUseful")
	private String DesignUseful;

	@SQLInteger(name = "BuildYear")
	private Integer BuildYear;

	@SQLString(name = "PropertyRightUnit")
	private String PropertyRightUnit;

	@SQLString(name = "RealPropertyRightUnit")
	private String RealPropertyRightUnit;

	@SQLString(name = "PropertyCardUnit")
	private String PropertyCardUnit;

	@SQLString(name = "GlebeCardUnit")
	private String GlebeCardUnit;

	@SQLString(name = "TransferUnit")
	private String TransferUnit;

	@SQLString(name = "GlebeCardNo")
	private String GlebeCardNo;

	@SQLString(name = "GlebeUseType")
	private String GlebeUseType;

	@SQLString(name = "GlebeEndDate")
	private String GlebeEndDate;

	@SQLString(name = "GlebeTypeUseful")
	private String GlebeTypeUseful;

	@SQLString(name = "PlanUseful")
	private String PlanUseful;

	@SQLString(name = "BefromFile")
	private String BefromFile;

	@SQLString(name = "NoPRNResion")
	private String NoPRNResion;

	@SQLString(name = "NoGCResion")
	private String NoGCResion;

	@SQLString(name = "RealEstateNo")
	private String RealEstateNo;

	@SQLString(name = "PropertyMemo")
	private String PropertyMemo;

	@SQLFloat(name = "OriginalAmount")
	private Float OriginalAmount;

	@SQLFloat(name = "AllDepreciation")
	private Float AllDepreciation;

	@SQLFloat(name = "DepreciationYear")
	private Float DepreciationYear;

	@SQLFloat(name = "NetWorth")
	private Float NetWorth;

	@SQLFloat(name = "EvaluationPrice")
	private Float EvaluationPrice;

	@SQLFloat(name = "EvaluationSinglePrice")
	private Float EvaluationSinglePrice;

	@SQLDateTime(name = "EvaluationPlace")
	private Date EvaluationPlace;

	@SQLFloat(name = "BefromAmount")
	private Float BefromAmount;

	@SQLFloat(name = "MarketHire")
	private Float MarketHire;

	@SQLString(name = "EvaluationUnit")
	private String EvaluationUnit;

	@SQLString(name = "EvaluationNo")
	private String EvaluationNo;

	@SQLString(name = "IsPawn")
	private String IsPawn;

	@SQLString(name = "PawnUnit")
	private String PawnUnit;

	@SQLString(name = "OriginalUnit")
	private String OriginalUnit;

	@SQLString(name = "FinanceMemo")
	private String FinanceMemo;

	@SQLString(name = "Utility")
	private String Utility;

	@SQLString(name = "ChartGUID")
	private String ChartGUID;

	@SQLString(name = "AddressCode")
	private String AddressCode;

	@SQLString(name = "OriginalAddressCode")
	private String OriginalAddressCode;

	@SQLString(name = "SecurityClassification")
	private String SecurityClassification;

	@SQLString(name = "HiddenDanger")
	private String HiddenDanger;

	@SQLString(name = "ResponsiblePerson")
	private String ResponsiblePerson;

	@SQLString(name = "sMemo")
	private String sMemo;

	@SQLString(name = "FileIndex")
	private String FileIndex;

	@SQLString(name = "SecurityRegion")
	private String SecurityRegion;

	@SQLInteger(name = "RoomCount")
	private Integer RoomCount;

	@SQLFloat(name = "LandArea")
	private Float LandArea;

	@SQLFloat(name = "UseYears")
	private Float UseYears;

	@SQLDateTime(name = "hidden_check_date")
	private Date hidden_check_date;

	@SQLDateTime(name = "asset_check_date")
	private Date asset_check_date;

	@SQLInteger(name = "IsHidden")
	private Integer IsHidden;

	@SQLInteger(name = "neaten_flow")
	private Integer neaten_flow;
}
