package biliman.java.coronavirustracker.controllers;

import biliman.java.coronavirustracker.models.LocationStats;
import biliman.java.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class HomeController {

    NumberFormat myFormat = NumberFormat.getInstance();

    @Autowired
    CoronaVirusDataService coronaVirusDataService; // From services

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCase()).sum(); // get sum of latestTotalCase
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", myFormat.format(totalReportedCases));
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";

    }
}
