package com.nuance.imaging.ops.opsclient.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ConversionParameters")
@Namespace(reference="http://www.nuance.com/2011/ConversionParameters")
public class ConversionParameters {
	@Element(name="ImageQuality")
	private String imageQuality;
	
	@Element(name="LogicalFormRecognition")
	private String logicalFormRecognition;
	
	@Element(name="Language")
	private String language;
	
	@Element(name="Rotation")
	private String rotation;
	
	@Element(name="Deskew")
	private String deskew;
	
	@Element(name="TradeOff")
	private String tradeOff;
	
	@Element(name="LayoutTradeOff")
	private String layoutTradeOff;
	
	@Element(name="PDFCompatibility")
	private String pdfCompatibility;
	
	@Element(name="CacheInputForReuse")
	private String cacheInputForReuse;
	
	@Element(name="CameraImage")
	private String cameraImage;
	
	
	public String getImageQuality() {
		return imageQuality;
	}
	public void setImageQuality(String imageQuality) {
		this.imageQuality = imageQuality;
	}
	public String getLogicalFormRecognition() {
		return logicalFormRecognition;
	}
	public void setLogicalFormRecognition(String logicalFormRecognition) {
		this.logicalFormRecognition = logicalFormRecognition;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRotation() {
		return rotation;
	}
	public void setRotation(String rotation) {
		this.rotation = rotation;
	}
	public String getDeskew() {
		return deskew;
	}
	public void setDeskew(String deskew) {
		this.deskew = deskew;
	}
	public String getTradeOff() {
		return tradeOff;
	}
	public void setTradeOff(String tradeOff) {
		this.tradeOff = tradeOff;
	}
	public String getLayoutTradeOff() {
		return layoutTradeOff;
	}
	public void setLayoutTradeOff(String layoutTradeOff) {
		this.layoutTradeOff = layoutTradeOff;
	}
	public String getPdfCompatibility() {
		return pdfCompatibility;
	}
	public void setPdfCompatibility(String pdfCompatibility) {
		this.pdfCompatibility = pdfCompatibility;
	}
	public String getCacheInputForReuse() {
		return cacheInputForReuse;
	}
	public void setCacheInputForReuse(String cacheInputForReuse) {
		this.cacheInputForReuse = cacheInputForReuse;
	}
	public String getCameraImage() {
		return cameraImage;
	}
	public void setCameraImage(String cameraImage) {
		this.cameraImage = cameraImage;
	}
	
}
