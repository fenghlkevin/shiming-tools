package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.util.SBase64;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fengh on 2016/5/13.
 */
//@Service
public class ExecuteWordService {

    public XWPFDocument execute(List<ExecuteParseExcelService.Student> students, ExecuteParams params) throws IOException {
        XWPFDocument templateDoc = new XWPFDocument(new ByteArrayInputStream(SBase64.decode(params.getWord_template())));

        XWPFDocument targetDoc = new XWPFDocument(new ByteArrayInputStream(SBase64.decode(params.getWord_template())));

        List<XWPFParagraph> templateDocParagraphs = templateDoc.getParagraphs();

        int count=0;
        for (ExecuteParseExcelService.Student student : students) {
            List<XWPFParagraph> paragraphs=null;
            if(count==0){
                count++;
                paragraphs=targetDoc.getParagraphs();
            }else{
                paragraphs=new ArrayList<XWPFParagraph>();
                for (int i = 0; i < templateDocParagraphs.size(); i++) {

                    XWPFParagraph t = templateDocParagraphs.get(i);
                    XWPFParagraph srcp = targetDoc.createParagraph();
                    paragraphs.add(srcp);
                    int index = targetDoc.getParagraphs().size() - 1;
                    targetDoc.setParagraph(t, index);
                    if (index % templateDocParagraphs.size() == 0) {
                        srcp.setPageBreak(true);
                    }
                }
            }

            this.replaceInParagraphs(student.getReplaceMap(),paragraphs);

        }

        return targetDoc;

    }

    public static void main(String[] args) throws IOException {
        XWPFDocument srcDoc = new XWPFDocument(new FileInputStream("C:\\Users\\fengh\\personal\\temp\\word_template.docx"));
        XWPFDocument temp = new XWPFDocument(new FileInputStream("C:\\Users\\fengh\\personal\\temp\\word_template.docx"));
        //XWPFDocument destDoc = new XWPFDocument();

        List<XWPFParagraph> template = temp.getParagraphs();

        for (int x = 0; x < 2; x++) {
            for (int i = 0; i < template.size(); i++) {
                XWPFParagraph t = template.get(i);
                XWPFParagraph srcp = srcDoc.createParagraph();
                int index = srcDoc.getParagraphs().size() - 1;
                srcDoc.setParagraph(t, index);
                if (index % template.size() == 0) {
                    srcp.setPageBreak(true);
                }
            }
        }


//    srcDoc.getParagraphArray(size).setPageBreak(true);

        OutputStream out = new FileOutputStream("C:\\Users\\fengh\\personal\\temp\\Destination.docx");
        srcDoc.write(out);
        out.flush();
        out.close();


    }


    private long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
        long count = 0;
        for (XWPFParagraph paragraph : xwpfParagraphs) {
            List<XWPFRun> runs = paragraph.getRuns();

            for (Map.Entry<String, String> replPair : replacements.entrySet()) {
                String find = replPair.getKey();
                String repl = replPair.getValue();
                TextSegement found = paragraph.searchText(find, new PositionInParagraph());
                if (found != null) {
                    count++;
                    if (found.getBeginRun() == found.getEndRun()) {
                        // whole search string is in one Run
                        XWPFRun run = runs.get(found.getBeginRun());
                        String runText = run.getText(run.getTextPosition());
                        String replaced = runText.replace(find, repl); //TODO do here
                        run.setText(replaced, 0);
                    } else {
                        // The search string spans over more than one Run
                        // Put the Strings together
                        StringBuilder b = new StringBuilder();
                        for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                            XWPFRun run = runs.get(runPos);
                            b.append(run.getText(run.getTextPosition()));
                        }
                        String connectedRuns = b.toString();
                        String replaced = connectedRuns.replace(find, repl);

                        // The first Run receives the replaced String of all connected Runs
                        XWPFRun partOne = runs.get(found.getBeginRun());
                        partOne.setText(replaced, 0);//TODO do here
                        // Removing the text in the other Runs.
                        for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                            XWPFRun partNext = runs.get(runPos);
                            partNext.setText("", 0);
                        }
                    }
                }
            }
        }
        return count;
    }
}
