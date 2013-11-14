package org.celllife.mosaic.interfaces.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class ReportController {

    @Value('${external.base.url}')
    def String externalBaseUrl;

    @RequestMapping("/")
    def index(Model model) {
        getReports(model)
    }

    @RequestMapping(value="index", method = RequestMethod.GET)
    def getReports(Model model) {

        def reports = org.celllife.mosaic.framework.restclient.RESTClient.get("${externalBaseUrl}/service/reports")
        model.put("reports", reports)
        return "index";

    }

}
