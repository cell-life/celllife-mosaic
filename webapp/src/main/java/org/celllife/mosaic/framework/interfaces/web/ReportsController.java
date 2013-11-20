package org.celllife.mosaic.framework.interfaces.web;

import org.celllife.pconfig.model.Pconfig;
import org.celllife.reporting.service.PconfigParameterHtmlService;
import org.celllife.reporting.service.ReportService;
import org.celllife.reporting.service.impl.PconfigParameterHtmlServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

@Controller
public class ReportsController {

    @Autowired
    private ReportService reportService;

    private PconfigParameterHtmlService pconfigParameterHtmlService = new PconfigParameterHtmlServiceImpl();

    private static Logger log = LoggerFactory.getLogger(ReportsController.class);

    @ResponseBody
    @RequestMapping(
            value = "/service/reports",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<Pconfig> findUssdVisits() {
        return reportService.getReports();
    }

    @ResponseBody
    @RequestMapping(value = "/service/getHtml", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getHtmlForReport(@RequestParam("reportId") String reportId) throws Exception {
        Pconfig pconfig;
        try {
            pconfig = reportService.getReportByName(reportId);
        } catch (Exception e) {
            return "No such Report.";
        }
        String htmlString = pconfigParameterHtmlService.createHtmlFieldsFromPconfig(pconfig, "submitButton");
        return htmlString;
    }

    @ResponseBody
    @RequestMapping(value = "/service/pdfReport", method = RequestMethod.GET, produces = "application/pdf")
    public void getPdfReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("Request Received. " + request.getParameterMap().toString());

        String reportId = request.getParameter("reportId");
        if (reportId.isEmpty()) {
            throw new RuntimeException("Could not retrieve this report.");
        } else {

            Pconfig pconfig = reportService.getReportByName(reportId);
            Pconfig returnedPconfig = pconfigParameterHtmlService.createPconfigFromHtmlFormSubmission(request.getParameterNames(), request.getParameterMap(), pconfig);

            //String generatedReport = reportService.generateReport(pconfig);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");
            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(new BufferedOutputStream(response.getOutputStream()));
                out.write("Hello");
            } catch (IOException e) {
                throw new RuntimeException("Could not create PDF.", e);
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    log.warn("Ignoring IOException thrown when trying to close and flush the OutputStream.", e.getMessage());
                }
            }
        }
    }

}