package ai.saxatus.aiml.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XMLUtilsTest
{
    @Mock
    Node node1;

    @BeforeEach
    protected void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        when(node1.getNodeType()).thenReturn(Node.ELEMENT_NODE);
        when(node1.getNodeName()).thenReturn("a");
        when(node1.getAttributes()).thenReturn(mock(NamedNodeMap.class));
        when(node1.getTextContent()).thenReturn("text");
    }

    @Test
    void testParseXMLToStringNodeList() throws Exception
    {
        NodeList nodeList = mock(NodeList.class);
        when(nodeList.getLength()).thenReturn(2);
        when(nodeList.item(0)).thenReturn(node1);
        when(nodeList.item(1)).thenReturn(node1);

        String response = XMLUtils.parseXMLToString(nodeList);
        assertEquals("<a/><a/>", response.replace("\r", "")
                        .replace("\n", ""));
    }

    @Test
    void testParseXMLToStringNode() throws Exception
    {

        String response = XMLUtils.parseXMLToString(node1);
        assertEquals("<a/>", response.replace("\r", "")
                        .replace("\n", ""));
    }

    @Test
    void testParseStringToXMLNode() throws Exception
    {
        Node response = XMLUtils.parseStringToXMLNode("text", "a");
        assertEquals("a", response.getNodeName());
        assertEquals("text", response.getTextContent());
    }

    @Test
    void testParseFileToXMLDocument() throws Exception
    {
        File file = new File(this.getClass()
                        .getResource("/test.xml")
                        .getFile());
        Document response = XMLUtils.parseFileToXMLDocument(file);
        assertEquals("#document", response.getNodeName());
        assertEquals(1, response.getChildNodes()
                        .getLength());
        Node n = response.getFirstChild();
        assertEquals("a", n.getNodeName());
        assertEquals("text", n.getTextContent());
    }
}
