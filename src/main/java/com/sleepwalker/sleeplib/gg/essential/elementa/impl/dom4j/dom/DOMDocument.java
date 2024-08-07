/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.dom;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.DocumentFactory;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.QName;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.tree.DefaultDocument;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;

/**
 * <p>
 * <code>DOMDocument</code> implements an XML document which supports the W3C
 * DOM API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.17 $
 */
public class DOMDocument extends DefaultDocument implements Document {
    /** The <code>DocumentFactory</code> instance used by default */
    private static final DOMDocumentFactory DOCUMENT_FACTORY
            = (DOMDocumentFactory) DOMDocumentFactory.getInstance();

    public DOMDocument() {
        init();
    }

    public DOMDocument(String name) {
        super(name);
        init();
    }

    public DOMDocument(DOMElement rootElement) {
        super(rootElement);
        init();
    }

    public DOMDocument(DOMDocumentType docType) {
        super(docType);
        init();
    }

    public DOMDocument(DOMElement rootElement, DOMDocumentType docType) {
        super(rootElement, docType);
        init();
    }

    public DOMDocument(String name, DOMElement rootElement,
            DOMDocumentType docType) {
        super(name, rootElement, docType);
        init();
    }

    private void init() {
        setDocumentFactory(DOCUMENT_FACTORY);
    }

    // org.w3c.dom.Node interface
    // -------------------------------------------------------------------------
    public boolean supports(String feature, String version) {
        return DOMNodeHelper.supports(this, feature, version);
    }

    public String getNamespaceURI() {
        return DOMNodeHelper.getNamespaceURI(this);
    }

    public String getPrefix() {
        return DOMNodeHelper.getPrefix(this);
    }

    public void setPrefix(String prefix) throws DOMException {
        DOMNodeHelper.setPrefix(this, prefix);
    }

    public String getLocalName() {
        return DOMNodeHelper.getLocalName(this);
    }

    public String getNodeName() {
        return "#document";
    }

    // already part of API
    //
    // public short getNodeType();
    public String getNodeValue() throws DOMException {
        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException {
    }

    public org.w3c.dom.Node getParentNode() {
        return DOMNodeHelper.getParentNode(this);
    }

    public NodeList getChildNodes() {
        return DOMNodeHelper.createNodeList(content());
    }

    public org.w3c.dom.Node getFirstChild() {
        return DOMNodeHelper.asDOMNode(node(0));
    }

    public org.w3c.dom.Node getLastChild() {
        return DOMNodeHelper.asDOMNode(node(nodeCount() - 1));
    }

    public org.w3c.dom.Node getPreviousSibling() {
        return DOMNodeHelper.getPreviousSibling(this);
    }

    public org.w3c.dom.Node getNextSibling() {
        return DOMNodeHelper.getNextSibling(this);
    }

    public NamedNodeMap getAttributes() {
        return null;
    }

    public org.w3c.dom.Document getOwnerDocument() {
        return null;
    }

    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild,
            org.w3c.dom.Node refChild) throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.insertBefore(this, newChild, refChild);
    }

    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild,
            org.w3c.dom.Node oldChild) throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.replaceChild(this, newChild, oldChild);
    }

    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild)
            throws DOMException {
        return DOMNodeHelper.removeChild(this, oldChild);
    }

    public org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild)
            throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.appendChild(this, newChild);
    }

    private void checkNewChildNode(org.w3c.dom.Node newChild)
            throws DOMException {
        final int nodeType = newChild.getNodeType();

        if (!((nodeType == org.w3c.dom.Node.ELEMENT_NODE)
                || (nodeType == org.w3c.dom.Node.COMMENT_NODE)
                || (nodeType == org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE) 
                || (nodeType == org.w3c.dom.Node.DOCUMENT_TYPE_NODE))) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Given node cannot be a child of document");
        }
    }

    public boolean hasChildNodes() {
        return nodeCount() > 0;
    }

    public org.w3c.dom.Node cloneNode(boolean deep) {
        return DOMNodeHelper.cloneNode(this, deep);
    }

    public boolean isSupported(String feature, String version) {
        return DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes() {
        return DOMNodeHelper.hasAttributes(this);
    }

    // org.w3c.dom.Document interface
    // -------------------------------------------------------------------------
    public NodeList getElementsByTagName(String name) {
        ArrayList<com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.Node> list = new ArrayList<com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.Node>();
        DOMNodeHelper.appendElementsByTagName(list, this, name);

        return DOMNodeHelper.createNodeList(list);
    }

    public NodeList getElementsByTagNameNS(String namespace, String name) {
        ArrayList<com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.Node> list = new ArrayList<com.sleepwalker.sleeplib.gg.essential.elementa.impl.dom4j.Node>();
        DOMNodeHelper.appendElementsByTagNameNS(list, this, namespace, name);

        return DOMNodeHelper.createNodeList(list);
    }

    public org.w3c.dom.DocumentType getDoctype() {
        return DOMNodeHelper.asDOMDocumentType(getDocType());
    }

    public org.w3c.dom.DOMImplementation getImplementation() {
        if (getDocumentFactory() instanceof org.w3c.dom.DOMImplementation) {
            return (org.w3c.dom.DOMImplementation) getDocumentFactory();
        } else {
            return DOCUMENT_FACTORY;
        }
    }

    public org.w3c.dom.Element getDocumentElement() {
        return DOMNodeHelper.asDOMElement(getRootElement());
    }

    public org.w3c.dom.Element createElement(String name) throws DOMException {
        return (org.w3c.dom.Element) getDocumentFactory().createElement(name);
    }

    public org.w3c.dom.DocumentFragment createDocumentFragment() {
        DOMNodeHelper.notSupported();

        return null;
    }

    public org.w3c.dom.Text createTextNode(String data) {
        return (org.w3c.dom.Text) getDocumentFactory().createText(data);
    }

    public org.w3c.dom.Comment createComment(String data) {
        return (org.w3c.dom.Comment) getDocumentFactory().createComment(data);
    }

    public CDATASection createCDATASection(String data) throws DOMException {
        return (CDATASection) getDocumentFactory().createCDATA(data);
    }

    public ProcessingInstruction createProcessingInstruction(String target,
            String data) throws DOMException {
        return (ProcessingInstruction) getDocumentFactory()
                .createProcessingInstruction(target, data);
    }

    public Attr createAttribute(String name) throws DOMException {
        QName qname = getDocumentFactory().createQName(name);

        return (Attr) getDocumentFactory().createAttribute(null, qname, "");
    }

    public EntityReference createEntityReference(String name)
            throws DOMException {
        return (EntityReference) getDocumentFactory().createEntity(name, null);
    }

    public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode,
            boolean deep) throws DOMException {
        DOMNodeHelper.notSupported();

        return null;
    }

    public org.w3c.dom.Element createElementNS(String namespaceURI,
            String qualifiedName) throws DOMException {
        QName qname = getDocumentFactory().createQName(qualifiedName,
                namespaceURI);

        return (org.w3c.dom.Element) getDocumentFactory().createElement(qname);
    }

    public org.w3c.dom.Attr createAttributeNS(String namespaceURI,
            String qualifiedName) throws DOMException {
        QName qname = getDocumentFactory().createQName(qualifiedName,
                namespaceURI);

        return (org.w3c.dom.Attr) getDocumentFactory().createAttribute(null,
                qname, null);
    }

    public org.w3c.dom.Element getElementById(String elementId) {
        return DOMNodeHelper.asDOMElement(elementByID(elementId));
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected DocumentFactory getDocumentFactory() {
        if (super.getDocumentFactory() == null) {
            return DOCUMENT_FACTORY;
        } else {
            return super.getDocumentFactory();
        }
    }

    public String getInputEncoding() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public String getXmlEncoding() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean getXmlStandalone() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public String getXmlVersion() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setXmlVersion(String xmlVersion) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public boolean getStrictErrorChecking() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public void setStrictErrorChecking(boolean strictErrorChecking) {
        DOMNodeHelper.notSupported();
    }

    public String getDocumentURI() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setDocumentURI(String documentURI) {
        DOMNodeHelper.notSupported();
    }

    public Node adoptNode(Node source) throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
    }

    public DOMConfiguration getDomConfig() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void normalizeDocument() {
        DOMNodeHelper.notSupported();
    }

    public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
    }

    public String getBaseURI() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public short compareDocumentPosition(Node other) throws DOMException {
        DOMNodeHelper.notSupported();
        return 0;
    }

    public String getTextContent() throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setTextContent(String textContent) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public boolean isSameNode(Node other) {
        DOMNodeHelper.notSupported();
        return false;
    }

    public String lookupPrefix(String namespaceURI) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean isDefaultNamespace(String namespaceURI) {
        DOMNodeHelper.notSupported();
        return false;
    }

    public String lookupNamespaceURI(String prefix) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean isEqualNode(Node other) {
        DOMNodeHelper.notSupported();
        return false;
    }

    public Object getFeature(String feature, String version) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public Object setUserData(String key, Object data, UserDataHandler handler) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public Object getUserData(String key) {
        DOMNodeHelper.notSupported();
        return null;
    }
}

/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project - http://www.dom4j.org
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 */
