package com.saxatus.aiml.tags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class LearnTag extends AbstractBotTag
{
    // TODO: rework

    private LearnTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "learn";

    public static void register()
    {
        TagFactory.addTag(TAG, LearnTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String pattern = "";
        String template = "";
        NodeList nodes = getNode().getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if (node.getNodeName()
                            .equals("category"))
            {
                NodeList nodes2 = node.getChildNodes();
                for (int i2 = 0; i2 < nodes2.getLength(); i2++)
                {
                    Node node2 = nodes2.item(i2);
                    if (node2.getNodeName()
                                    .equals("pattern"))
                    {
                        pattern = getFactory().createTag(node2)
                                        .handle(this.getAIMLParseNode());
                    }
                    if (node2.getNodeName()
                                    .equals("template"))
                    {
                        template = getFactory().createTag(node2)
                                        .handle(this.getAIMLParseNode());
                    }
                }
            }
        }
        try
        {

            updateAIML(pattern, template);
        }
        catch(Exception e)
        {
            Logger.getLogger(this.getClass()
                            .getName())
                            .log(Level.SEVERE, "Exception in LearnTag.handle: ", e);
        }
        // bot.setReinitFlag(true);
        return "";
    }

    private void updateAIML(String pattern, String template) throws Exception
    {
        File learnFile = getAIMLHandler().getLearnFile();
        if (!learnFile.getParentFile()
                        .exists())
        {
            learnFile.getParentFile()
                            .mkdirs();
        }
        if (!learnFile.exists())
        {
            learnFile.createNewFile();
            try (PrintStream out = new PrintStream(new FileOutputStream(learnFile)))
            {
                out.print("<aiml></aiml>");
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(learnFile);
        Node node = doc.createElement("category");
        node.appendChild(doc.createElement("pattern"))
                        .appendChild(doc.createTextNode(pattern));
        node.appendChild(doc.createElement("template"))
                        .appendChild(doc.createTextNode(template));
        node.appendChild(doc.createComment("taught by " + nonStaticMemory.get("name")));
        doc.getElementsByTagName("aiml")
                        .item(0)
                        .appendChild(node);
        Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer();
        Result output = new StreamResult(learnFile);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }

}
