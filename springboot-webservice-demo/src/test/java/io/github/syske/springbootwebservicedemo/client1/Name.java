
package io.github.syske.springbootwebservicedemo.client1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>name complex type�� Java �ࡣ
 *
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="name"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="myname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "name", propOrder = {
        "myname"
})
public class Name {

    protected String myname;

    /**
     * ��ȡmyname���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getMyname() {
        return myname;
    }

    /**
     * ����myname���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMyname(String value) {
        this.myname = value;
    }

}
