package lab10;

import java.util.Scanner;
import java.util.*;


public class Document implements IWithName
{
    public String name;
    public SortedMap<String, Link> link;

    public Document(String name)
    {
        this.name = name.toLowerCase();
        link = new TreeMap<>();
    }

    public Document(String name, Scanner scan)
    {
        this.name = name.toLowerCase();
        link = new TreeMap<>();
        load(scan);
    }

    public void load(Scanner scan)
    {
        String marker = "link=";
        String endMarker = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(endMarker))
        {
            String arr[] = line.split(" ");
            for (String word : arr)
            {
                if (word.startsWith(marker))
                {
                    String linkStr = word.substring(marker.length());
                    Link l;
                    if ((l = createLink(linkStr)) != null)
                    {
                        link.put(l.ref, l);
                    }
                }

            }
            line = scan.nextLine().toLowerCase();
        }

    }

    public static boolean isCorrectId(String id)
    {
        id = id.toLowerCase();
        if (id.length() == 0)
        {
            return false;
        }
        if (id.charAt(0) < 'a' || id.charAt(0) > 'z')
        {
            return false;
        }
        for (int i = 1; i < id.length(); i++)
        {
            if (!(id.charAt(i) >= 'a' && id.charAt(i) <= 'z' || id.charAt(i) >= '0' && id.charAt(i) <= '9' || id.charAt(i) == '_'))
            {
                return false;
            }
        }
        return true;
    }


    private static Link createIdAndNumber(String id, int n)
    {
        if (!isCorrectId(id))
        {
            return null;
        }
        return new Link(id.toLowerCase(), n);
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLink(String link)
    {
        if (link.length() == 0)
        {
            return null;
        }
        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');
        if (openBracket > 0 && closeBracket > openBracket && closeBracket == link.length() - 1)
        {
            String strNumber = link.substring(openBracket + 1, closeBracket);
            try
            {
                int number = Integer.parseInt(strNumber);
                if (number < 1)
                {
                    return null;
                }
                return createIdAndNumber(link.substring(0, openBracket), number);
            }
            catch (NumberFormatException ex)
            {
                return null;
            }
        }
        return createIdAndNumber(link, 1);
    }

    @Override
    public String toString()
    {
        String retStr = "Document: " + name + "\n ";
//TODO?
        retStr += link;
        return retStr;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String getName()
    {
        return name;
    }
}
