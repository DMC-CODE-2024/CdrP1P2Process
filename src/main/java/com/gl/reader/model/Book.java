package com.gl.reader.model;

public class Book {
	private String IMEI;
	private String IMSI;
	private String MSISDN;
	private String recordType;
	private String systemType;
	private String sourceName;
	private String fileName;
	private String eventTime;
	public Book(String iMEI, String iMSI, String mSISDN, String recordType, String systemType, String sourceName,
			String fileName, String eventTime) {
		super();
		IMEI = iMEI;
		IMSI = iMSI;
		MSISDN = mSISDN;
		this.recordType = recordType;
		this.systemType = systemType;
		this.sourceName = sourceName;
		this.fileName = fileName;
		this.eventTime = eventTime;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getMSISDN() {
		return MSISDN;
	}
	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IMEI == null) ? 0 : IMEI.hashCode());
		result = prime * result + ((IMSI == null) ? 0 : IMSI.hashCode());
		result = prime * result + ((MSISDN == null) ? 0 : MSISDN.hashCode());
		result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((recordType == null) ? 0 : recordType.hashCode());
		result = prime * result + ((sourceName == null) ? 0 : sourceName.hashCode());
		result = prime * result + ((systemType == null) ? 0 : systemType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (IMEI == null) {
			if (other.IMEI != null)
				return false;
		} else if (!IMEI.equals(other.IMEI))
			return false;
		if (IMSI == null) {
			if (other.IMSI != null)
				return false;
		} else if (!IMSI.equals(other.IMSI))
			return false;
		if (MSISDN == null) {
			if (other.MSISDN != null)
				return false;
		} else if (!MSISDN.equals(other.MSISDN))
			return false;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (recordType == null) {
			if (other.recordType != null)
				return false;
		} else if (!recordType.equals(other.recordType))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		if (systemType == null) {
			if (other.systemType != null)
				return false;
		} else if (!systemType.equals(other.systemType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Book [IMEI=" + IMEI + ", IMSI=" + IMSI + ", MSISDN=" + MSISDN + ", recordType=" + recordType
				+ ", systemType=" + systemType + ", sourceName=" + sourceName + ", fileName=" + fileName
				+ ", eventTime=" + eventTime + "]";
	}



	public static Book createBook(String IMEI, String IMSI, String MSISDN, String system_type, String record_type,
								   String source_name, String file_name, String event_time) {
		String msisdn = (( MSISDN.trim().startsWith("00"))
				? MSISDN.substring(2)
				: MSISDN).replace("1AO", "855");
		return new Book(IMEI, IMSI, msisdn, record_type, system_type, source_name, file_name, event_time);
	}

	
}
