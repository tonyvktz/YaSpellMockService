package my.tz.yaspellmockservice.contract;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-08-23T22:44:00.575+03:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://tomee.local/services/spellMock/services", name = "SpellServiceSoap")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SpellServiceSoap {

    @WebMethod(action = "http://tomee.local/services/spellMock/services/checkTexts")
    @WebResult(name = "CheckTextsResponse", targetNamespace = "http://tomee.local/services/spellMock/services", partName = "parameters")
    public CheckTextsResponse checkTexts(
        @WebParam(partName = "parameters", name = "CheckTextsRequest", targetNamespace = "http://tomee.local/services/spellMock/services")
        CheckTextsRequest parameters
    );

    @WebMethod(action = "http://tomee.local/services/spellMock/services/checkText")
    @WebResult(name = "CheckTextResponse", targetNamespace = "http://tomee.local/services/spellMock/services", partName = "parameters")
    public CheckTextResponse checkText(
        @WebParam(partName = "parameters", name = "CheckTextRequest", targetNamespace = "http://tomee.local/services/spellMock/services")
        CheckTextRequest parameters
    );
}