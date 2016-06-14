package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.framework.util.ObjUtil;
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

    public byte[] execute(List<ExecuteParseExcelService.Student> students, ExecuteParams params) throws IOException {
        XWPFDocument targetDoc = new XWPFDocument(new ByteArrayInputStream(SBase64.decode(params.getWord_template())));

        XWPFDocument templateDoc = new XWPFDocument(new ByteArrayInputStream(SBase64.decode(params.getWord_template())));
        List<XWPFParagraph> templateDocParagraphs = templateDoc.getParagraphs();

        int count = 0;
        for (ExecuteParseExcelService.Student student : students) {
            List<XWPFParagraph> paragraphs = null;
            if (count == 0) {
                count++;
                paragraphs = targetDoc.getParagraphs();
            } else {

                paragraphs = new ArrayList<XWPFParagraph>();
                for (int i = 0; i < templateDocParagraphs.size(); i++) {
                    XWPFParagraph templateParagraph = templateDocParagraphs.get(i);
                    XWPFParagraph targetParagraph = targetDoc.createParagraph();
                    this.copyAllRunsToAnotherParagraph(templateParagraph, targetParagraph, i == templateDocParagraphs.size() - 1);
                    int index = targetDoc.getParagraphs().size() - 1;
                    targetDoc.setParagraph(targetParagraph, index);
                    if (index % templateDocParagraphs.size() == 0) {
                        targetParagraph.setPageBreak(true);
                    }

                    paragraphs.add(targetParagraph);
                }
            }
            System.out.println(student.getStudent_name());
//            if(student.getReplaceMap()==null){
                this.replaceInParagraphs(student.getReplaceMap(), paragraphs);
//            }


        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        targetDoc.write(out);

        return out.toByteArray();

    }

    // Copy all runs from one paragraph to another, keeping the style unchanged
    private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar, boolean isLast) {
        List<XWPFRun> oldruns = oldPar.getRuns();
        for (int i = 0; i < oldruns.size(); i++) {
            XWPFRun run = oldruns.get(i);
            XWPFRun newRun = newPar.createRun();
            newRun.getCTR().set(run.getCTR());

            if (i == 0) {
                if (isLast) {
                    newRun.setText("                                                        " + newRun.getText(0), 0);
                } else {
                    newRun.setText("    " + newRun.getText(0), 0);
                }

            }


        }
    }

//    public static void main(String[] args) throws IOException {
//
//        List<ExecuteParseExcelService.Student> students = new ArrayList<ExecuteParseExcelService.Student>();
//        ExecuteParseExcelService.Student mstudent = new ExecuteParseExcelService.Student();
//        Map<String, String> replaceMap = new HashMap<String, String>();
//        replaceMap.put("{student_name}", "李飞");
//        replaceMap.put("{sign_str}", "李飞的老公");
//        //replaceMap.put("space", "                                                           ");
//        replaceMap.put("{count}","5");
//        mstudent.setReplaceMap(replaceMap);
//        students.add(mstudent);
//
//        replaceMap = new HashMap<String, String>();
//        replaceMap.put("{student_name}", "冯贺亮");
//        replaceMap.put("{sign_str}", "冯贺亮的老婆");
//        replaceMap.put("{count}","51");
//        //replaceMap.put("{space}", "                                                           ");
//        mstudent = new ExecuteParseExcelService.Student();
//        mstudent.setReplaceMap(replaceMap);
//        students.add(mstudent);
//
//        ExecuteWordService executeWordService = new ExecuteWordService();
//
//        XWPFDocument targetDoc = new XWPFDocument(new FileInputStream("C:\\Users\\fengh\\personal\\temp\\word_template.docx"));
//
//        XWPFDocument templateDoc = new XWPFDocument(new FileInputStream("C:\\Users\\fengh\\personal\\temp\\word_template.docx"));
//        List<XWPFParagraph> templateDocParagraphs = templateDoc.getParagraphs();
//
//
//        int count = 0;
//        for (ExecuteParseExcelService.Student student : students) {
//            List<XWPFParagraph> paragraphs = null;
//            if (count == 0) {
//                count++;
//                paragraphs = targetDoc.getParagraphs();
//            } else {
//                paragraphs = new ArrayList<XWPFParagraph>();
//                for (int i = 0; i < templateDocParagraphs.size(); i++) {
//
//                    XWPFParagraph templateParagraph = templateDocParagraphs.get(i);
//                    XWPFParagraph targetParagraph = targetDoc.createParagraph();
//                    executeWordService.copyAllRunsToAnotherParagraph(templateParagraph, targetParagraph,i==templateDocParagraphs.size()-1);
//                    //targetParagraph.getCTP().set(templateParagraph.getCTP());
//                    int index = targetDoc.getParagraphs().size() - 1;
//                    targetDoc.setParagraph(targetParagraph, index);
//                    if (index % templateDocParagraphs.size() == 0) {
//                        targetParagraph.setPageBreak(true);
//                    }
//
//                    paragraphs.add(targetParagraph);
//                }
//            }
//
//            executeWordService.replaceInParagraphs(student.getReplaceMap(), paragraphs);
//
//        }
//
//        OutputStream out = new FileOutputStream("C:\\Users\\fengh\\personal\\temp\\Destination.docx");
//        targetDoc.write(out);
//        out.flush();
//        out.close();
//
//    }

    private static final String START_TAG = "{%";
    private int replacePattern(XWPFRun theRun, XWPFParagraph paragraph, int runPosition, String connectedRuns, String find, String repl) {
        int addRun=0;
        while (connectedRuns.indexOf(find) >= 0) {
            connectedRuns = connectedRuns.replace(find, repl);
        }
//         connectedRuns = connectedRuns.replace(find, repl);
        int start_index, end_index;
        boolean noPattern = true;
        theRun.setText("", 0);
        int nextRunPosition = runPosition + 1;
        while ((start_index = connectedRuns.indexOf(START_TAG)) >= 0 && (end_index = connectedRuns.indexOf(END_TAG)) >= 0) {
            noPattern = false;
            String beforePattern = connectedRuns.substring(0, start_index);
            String pattern = connectedRuns.substring(start_index, end_index + 2);
            String afterPattern = connectedRuns.substring(end_index + 2);

            XWPFRun newRun = null;
            if (!"".equalsIgnoreCase(beforePattern)) {
                newRun = paragraph.insertNewRun(nextRunPosition++);
                newRun.getCTR().set(theRun.getCTR());
                newRun.setText(beforePattern, 0);
                addRun++;
            }

            if (!"".equalsIgnoreCase(pattern)) {
                newRun = paragraph.insertNewRun(nextRunPosition++);
                newRun.getCTR().set(theRun.getCTR());
                addRun++;

                String _tempstr = pattern.substring(2, pattern.length() - 2);
                String vs[] = _tempstr.split("#");
                if (vs.length != 2) {
                    newRun.setText(pattern);
                } else {
                    newRun.setText(vs[1]);
                    String allCondition[] = vs[0].split(",");
                    for (String condition : allCondition) {
                        if ("bold".equalsIgnoreCase(condition)) {
                            newRun.setBold(true);
                        } else if ("underline".equalsIgnoreCase(condition)) {
                            newRun.setUnderline(UnderlinePatterns.SINGLE);
                        } else if (condition.startsWith("fontsize")) {
                            String t[] = condition.split(":");
                            if (t.length == 2) {
                                newRun.setFontSize(new Integer(t[1]));
                            }
                        } else if (condition.startsWith("font")) {
                            String t[] = condition.split(":");
                            if (t.length == 2) {
                                newRun.setFontFamily(t[1]);
                            }
                        } else if (condition.startsWith("color")) {
                            String t[] = condition.split(":");
                            if (t.length == 2) {
                                newRun.setColor("#" + t[1]);
                            }
                        }
                    }
                }
            }
            connectedRuns = afterPattern;
        }

        if (noPattern) {
            theRun.setText(connectedRuns, 0);
        }else{
            if(!ObjUtil.isEmpty(connectedRuns)){
                XWPFRun newRun = paragraph.insertNewRun(nextRunPosition++);
                newRun.getCTR().set(theRun.getCTR());
                newRun.setText(connectedRuns,0);
                addRun++;
            }
        }
        return addRun;
    }


    private static final String END_TAG = "%}";

    private long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
        long count = 0;
        for (XWPFParagraph paragraph : xwpfParagraphs) {
            List<XWPFRun> runs = paragraph.getRuns();

            for (Map.Entry<String, String> replPair : replacements.entrySet()) {
                String find = replPair.getKey();
                String repl = replPair.getValue();
                TextSegement found;
                while ((found = paragraph.searchText(find, new PositionInParagraph())) != null) {
                    count++;
                    if (found.getBeginRun() == found.getEndRun()) {
                        // whole search string is in one Run
                        XWPFRun run = runs.get(found.getBeginRun());

                        replacePattern(run, paragraph, found.getBeginRun(), run.getText(run.getTextPosition()), find, repl);

//                        String runText = run.getText(run.getTextPosition());
//                        String replaced = runText.replace(find, repl);
//                        run.setText(replaced, 0);

                    } else {
                        // The search string spans over more than one Run
                        // Put the Strings together
                        StringBuilder b = new StringBuilder();
                        for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                            XWPFRun run = runs.get(runPos);
                            b.append(run.getText(run.getTextPosition()));
                        }
                        int ocount=found.getEndRun()-found.getBeginRun();

                        XWPFRun partOne = runs.get(found.getBeginRun());

                        String connectedRuns = b.toString();
                        int addrun=replacePattern(partOne, paragraph, found.getBeginRun(), connectedRuns, find, repl);
                        // The first Run receives the replaced String of all connected Runs
//                        String replaced = connectedRuns.replace(find, repl);
//                        partOne.setText(replaced, 0);

                        // replaceWithNewRun(replList, partOne, paragraph, found.getBeginRun(), find);
                        // Removing the text in the other Runs.
                        int runPos = found.getBeginRun() +addrun+ 1;
                        int lastPos=found.getBeginRun() +addrun+ocount;
                        for (; runPos <= lastPos; runPos++) {
                            XWPFRun partNext = runs.get(runPos);
                            partNext.setText("", 0);
                        }
                    }
//                    found = paragraph.searchText(find, new PositionInParagraph());
                }
            }
        }
        return count;
    }


//    private long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
//        long count = 0;
//        for (XWPFParagraph paragraph : xwpfParagraphs) {
//            List<XWPFRun> runs = paragraph.getRuns();
//
//            for (Map.Entry<String, String> replPair : replacements.entrySet()) {
//                String find = replPair.getKey();
//                String repl = replPair.getValue();
//                TextSegement found = paragraph.searchText(find, new PositionInParagraph());
//                if (found != null) {
//                    count++;
//                    if (found.getBeginRun() == found.getEndRun()) {
//                        // whole search string is in one Run
//                        XWPFRun run = runs.get(found.getBeginRun());
//
//                        String runText = run.getText(run.getTextPosition());
//                        String replaced = runText.replace(find, repl); //TODO do here
//                        run.setText(replaced, 0);
//                    } else {
//                        // The search string spans over more than one Run
//                        // Put the Strings together
//                        StringBuilder b = new StringBuilder();
//                        for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
//                            XWPFRun run = runs.get(runPos);
//                            b.append(run.getText(run.getTextPosition()));
//                        }
//                        String connectedRuns = b.toString();
//                        String replaced = connectedRuns.replace(find, repl);
//
//                        // The first Run receives the replaced String of all connected Runs
//                        XWPFRun partOne = runs.get(found.getBeginRun());
//                        partOne.setText(replaced, 0);//TODO do here
//                        // Removing the text in the other Runs.
//                        for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
//                            XWPFRun partNext = runs.get(runPos);
//                            partNext.setText("", 0);
//                        }
//                    }
//                }
//            }
//        }
//        return count;
//    }
}
