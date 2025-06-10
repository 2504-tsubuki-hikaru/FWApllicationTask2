package com.example.forum2.service;

import com.example.forum2.controller.form.ReportForm;
import com.example.forum2.repository.ReportRepository;
import com.example.forum2.repository.entity.Report;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String start, String end) throws ParseException {

        String strStartDate;
        String strEndDate;

        if (!StringUtils.isBlank(start)) {
            strStartDate = start + " 00:00:00";
        }  else {
            strStartDate = "2020-01-01 00:00:00";
            }

        if (!StringUtils.isBlank(end)) {
            strEndDate = end + " 23:59:59";
        }  else {
            //endの中身が空の時は現在時刻を取得
            Date date =new Date();
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            strEndDate = sdFormat.format(date) + " 23:59:59";
            }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdFormat.parse(strStartDate);
        Date endDate = sdFormat.parse(strEndDate);

        List<Report> results = reportRepository. findByUpdatedDateBetweenOrderByUpdatedDateDesc(startDate, endDate);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        return report;
    }

    /*
     * レコード削除
     */
    public void  deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }

    /*
     * 編集したい投稿のレコード1件取得
     */
    public ReportForm editReport(Integer id) {
        //ReportForm型のListを作成。
        List<Report> results = new ArrayList<>();
        results.add((Report) reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        //リストの最初の一件目だけ返す。
        return reports.get(0);
    }
}

