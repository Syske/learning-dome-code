
package io.github.syske.springbootwebservicedemo.client1;

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
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Hello_QNAME = new QName("http://service.ws.sample/", "hello");
    private final static QName _Name_QNAME = new QName("http://service.ws.sample/", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.github.syske.cxf.client
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hello }
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link Name }
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.ws.sample/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Name }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link Name }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.ws.sample/", name = "name")
    public JAXBElement<Name> createName(Name value) {
        return new JAXBElement<Name>(_Name_QNAME, Name.class, null, value);
    }

}
