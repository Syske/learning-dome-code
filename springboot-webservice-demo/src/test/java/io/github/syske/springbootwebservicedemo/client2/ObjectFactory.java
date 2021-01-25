
package io.github.syske.springbootwebservicedemo.client2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.github.syske.cxf.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayHello2Param_QNAME = new QName("http://service.ws.sample/", "sayHello2Param");
    private final static QName _SayHello2Return_QNAME = new QName("http://service.ws.sample/", "sayHello2Return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.github.syske.cxf.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHello2Param }
     * 
     */
    public SayHello2Param createSayHello2Param() {
        return new SayHello2Param();
    }

    /**
     * Create an instance of {@link SayHello2Return }
     * 
     */
    public SayHello2Return createSayHello2Return() {
        return new SayHello2Return();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello2Param }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHello2Param }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.ws.sample/", name = "sayHello2Param")
    public JAXBElement<SayHello2Param> createSayHello2Param(SayHello2Param value) {
        return new JAXBElement<SayHello2Param>(_SayHello2Param_QNAME, SayHello2Param.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello2Return }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHello2Return }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.ws.sample/", name = "sayHello2Return")
    public JAXBElement<SayHello2Return> createSayHello2Return(SayHello2Return value) {
        return new JAXBElement<SayHello2Return>(_SayHello2Return_QNAME, SayHello2Return.class, null, value);
    }

}
