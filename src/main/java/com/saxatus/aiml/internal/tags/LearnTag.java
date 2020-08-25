package com.saxatus.aiml.internal.tags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.NonStaticMemoryUsingTag;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.XMLUtils;

@TagName("learn")
public class LearnTag extends AbstractAIMLTag implements NonStaticMemoryUsingTag
{
    private static final Log log = LogFactory.getLog(LearnTag.class);

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        AIML aiml = createAIML(context);
        try
        {
            updateLearnFile(context, aiml);
        }
        catch(Exception e)
        {
            log.error("Exception in LearnTag.handle: ", e);
        }
        return "";
    }

    AIML createAIML(AIMLParsingSessionContext context)
    {
        String pattern = "";
        String template = "";
        NodeList nodes = getXMLNode(context).getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node categoriesNode = nodes.item(i);
            if (categoriesNode.getNodeName()
                            .equals("category"))
            {
                NodeList categoryChildren = categoriesNode.getChildNodes();
                for (int i2 = 0; i2 < categoryChildren.getLength(); i2++)
                {
                    Node patternOrTemplateNode = categoryChildren.item(i2);
                    String nodeName = patternOrTemplateNode.getNodeName();
                    if (nodeName.equals("pattern"))
                    {
                        pattern = getSession(context).createTag(patternOrTemplateNode)
                                        .handle(context.of(debugNode, patternOrTemplateNode));
                    }
                    else if (nodeName.equals("template"))
                    {
                        template = getSession(context).createTag(patternOrTemplateNode)
                                        .handle(context.of(debugNode, patternOrTemplateNode));
                    }
                }
            }
        }
        return new AIML(pattern, template, null, null, "learned", -1);
    }

    private void updateLearnFile(AIMLParsingSessionContext context, AIML aiml)
                    throws IOException, ParserConfigurationException, SAXException, TransformerException
    {
        File learnFile = getAIMLHandler(context).getLearnFile();
        File parentFile = learnFile.getParentFile();
        if (!parentFile.exists())
        {
            parentFile.mkdirs();
        }
        if (!learnFile.exists())
        {
            createNewLearnFile(learnFile);
        }
        Document doc = XMLUtils.parseFileToXMLDocument(learnFile);
        Node node = doc.createElement("category");
        node.appendChild(doc.createElement("pattern"))
                        .appendChild(doc.createTextNode(aiml.getPattern()));
        node.appendChild(doc.createElement("template"))
                        .appendChild(doc.createTextNode(aiml.getTemplate()));
        node.appendChild(doc.createComment("taught by " + getNonStaticMemory(context).get("name")));
        doc.getElementsByTagName("aiml")
                        .item(0)
                        .appendChild(node);
        XMLUtils.writeXMLDocumentToFile(doc, learnFile);
    }

    private void createNewLearnFile(File learnFile) throws IOException
    {
        boolean success = (learnFile.createNewFile());
        if (!success)
        {
            log.warn("File already exists, while File::exists returned false. What did you do?");
        }
        try (PrintStream out = new PrintStream(new FileOutputStream(learnFile)))
        {
            out.print("<aiml></aiml>");
        }
        catch(FileNotFoundException e)
        {
            log.error("Can't write to learnFile " + learnFile.getAbsolutePath(), e);
        }

    }

}
