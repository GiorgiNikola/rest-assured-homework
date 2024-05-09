package ge.tbc.tbcitacademy.Models.Responses.Formula1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriversResponse{

	@JsonProperty("MRData")
	private MRData mRData;

	public void setMRData(MRData mRData){
		this.mRData = mRData;
	}

	public MRData getMRData(){
		return mRData;
	}
}