package club.playerfox.okhttptest;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class SAXParserHandler extends DefaultHandler{
    private String nodeName;
    private String to;
    private String from;
    private String heading;
    private String body;
    private Context mContext;

    public SAXParserHandler(Context context) {
        super();
        mContext = context;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("to".equals(nodeName)){
            to = new String(ch, start, length);
        }else if ("from".equals(nodeName)){
            from = new String(ch, start, length);
        }else if ("heading".equals(nodeName)){
            heading = new String(ch, start, length);
        }else if ("body".equals(nodeName)){
            body = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("note".equals(localName)){
            Log.e(to, from);
            String str = "to:" + to + "\nfrom:" + from + "\nheading:" + heading + "\nbody:" + body;
            try {
                Method method = MainActivity.class.getMethod("showText", String.class);
                method.invoke(mContext,str);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

}
