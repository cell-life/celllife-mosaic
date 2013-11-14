package org.celllife.mosaic.framework.interfaces.web;

import org.celllife.pconfig.model.Pconfig;
import org.celllife.reporting.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class ReportsController {

    @Autowired
    private ReportService reportService;

    private static Logger log = LoggerFactory.getLogger(ReportsController .class);

    @ResponseBody
    @RequestMapping(
            value = "/service/reports",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<Pconfig> findUssdVisits() {

        return reportService.getReports();

    }

}