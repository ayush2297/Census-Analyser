package mycensusadapters;

import censusanalyser.CensusAnalyser;
import censusanalyser.CensusAnalyserException;

public class CensusAdapterFactory {

    public static CensusAdapter getAdapter(CensusAnalyser.CountryName countryName) throws CensusAnalyserException {
        switch (countryName){
            case INDIA:
                return new IndiaCensusAdapter();
            case US:
                return new USCensusAdapter();
            default:
                throw new CensusAnalyserException("Incorrect Country entered",
                        CensusAnalyserException.ExceptionType.INCORRECT_COUNTRY_ENTERED);
        }
    }

}
