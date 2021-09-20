package fact.it.projectthemepark.controller;

import fact.it.projectthemepark.model.Attraction;
import fact.it.projectthemepark.model.Staff;
import fact.it.projectthemepark.model.ThemePark;
import fact.it.projectthemepark.model.Visitor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Controller
public class MainController {
//You will need these ArrayLists later on.
    private ArrayList<Staff> staffMembers;
    private ArrayList<Visitor> visitors;
    private ArrayList<ThemePark> themeParks;

//Write your code here
    @PostConstruct
    private void fillPark(){
        staffMembers = fillStaffMembers();
        visitors = fillVisitors();
        themeParks = fillThemeParks();
    }

    @RequestMapping("/1_NewVisitor")
    public String userInfo(Model model){
        model.addAttribute("parks", themeParks);
        return "1_NewVisitor";
    }

    @RequestMapping("/newvisitor")
    public String newVisitor(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstname");
        String surName = request.getParameter("surname");
        int birthday = Integer.parseInt(request.getParameter("birthday"));
        String park = request.getParameter("park");


        Visitor newVisitor = new Visitor(firstName, surName);
        newVisitor.setYearOfBirth(birthday);

        themeParks.get(Integer.parseInt(park)).registerVisitor(newVisitor);
        visitors.add(newVisitor);
        model.addAttribute("newVisitor", newVisitor);

        return "2_VisitorInfo";
    }

    @RequestMapping("/3_NewStaff")
    public String newStaff(Model model){
        model.addAttribute("parks", themeParks);
        return "3_NewStaff";
    }

    @RequestMapping("/newstaff")
    public String newStaff(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstname");
        String surName = request.getParameter("surname");
        LocalDate employedSince = LocalDate.parse(request.getParameter("employed_since"));
        boolean student = (request.getParameter("student") != null);
        int park = Integer.parseInt(request.getParameter("park"));


        if (park == -1) {
            model.addAttribute("errormessage", "You didn't pick a theme park!");
            return "error";
        }

        ThemePark employedAt = themeParks.get(park);
        Staff newStaff = new Staff(firstName, surName);
        newStaff.setStartDate(employedSince);
        newStaff.setStudent(student);
        newStaff.setEmployedAt(employedAt);

        staffMembers.add(newStaff);
        model.addAttribute("newStaff", newStaff);

        return "0_exam";
    }

    @RequestMapping("/5_StaffList")
    public String staffList(Model model){
        model.addAttribute("staffMembers", staffMembers);
        return "5_StaffList";
    }

    @RequestMapping("/6_VisitorList")
    public String visitorList(Model model){
        model.addAttribute("visitors", visitors);
        return "6_VisitorList";
    }

    @RequestMapping("/7_NewThemePark")
    public String newThemePark(){
        return "7_NewThemePark";
    }

    @RequestMapping("/newThemePark")
    public String allThemePark(HttpServletRequest request ,Model model){
        String newPark = request.getParameter("newparkname");
        ThemePark newThemePark = new ThemePark(newPark);
        themeParks.add(newThemePark);
        model.addAttribute("parks", themeParks);
        return "8_AllThemePark";
    }

    @RequestMapping("/8_AllThemePark")
    public String allParks(Model model){
        model.addAttribute("parks", themeParks);
        return "8_AllThemePark";
    }

    @RequestMapping("/9_NewAttraction")
    public String newAttraction(Model model){
        model.addAttribute("parks", themeParks);
        model.addAttribute("staffs", staffMembers);
        return "9_NewAttraction";
    }

    @RequestMapping("/newAttraction")
    public String allAttractions(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        String duration = request.getParameter("duration");
        String photo = request.getParameter("photo");
        int whichPark = Integer.parseInt(request.getParameter("park"));
        int whichStaff = Integer.parseInt(request.getParameter("staff"));

        if (whichStaff < 0){
            model.addAttribute("errormessage", "You didn't choose a staff member!");
            return "error";
        }

        if (whichPark < 0){
            model.addAttribute("errormessage", "You didn't choose a staff ThemePark!");
            return "error";
        }

        Attraction newAttraction = new Attraction(name, Integer.parseInt(duration));
        newAttraction.setPhoto(photo);
        newAttraction.setResponsible(staffMembers.get(whichStaff));
        themeParks.get(whichPark).addAttraction(newAttraction);

        model.addAttribute("park", themeParks.get(whichPark));
        return "10_AllAttraction";
    }

    @RequestMapping("/10_AllAttraction")
    public String allAttraction2(HttpServletRequest request, Model model){
        int whichPark = Integer.parseInt(request.getParameter("parkIndex"));
        ThemePark park = themeParks.get(whichPark);
        model.addAttribute("park", park);

        return "10_AllAttraction";
    }

    @RequestMapping("/search")
    public String search(HttpServletRequest request, Model model){
        String searchTerm = request.getParameter("search");
        for (ThemePark themePark: themeParks){
            Attraction result = themePark.searchAttractionByName(searchTerm);
            if (result != null){
                model.addAttribute("attraction", result);
                return "11_Searched";
            }
        }

        model.addAttribute("errormessage", String.format("There is no attraction with the name \"%s\"", searchTerm));
        return "error";
    }

//You will need these methods later on.
    private ArrayList<Staff> fillStaffMembers() {
        ArrayList<Staff> staffMembers = new ArrayList<>();
        Staff staff1 = new Staff("Johan", "Bertels");
        staff1.setStartDate(LocalDate.of(2002, 5, 1));
        Staff staff2 = new Staff("An", "Van Herck");
        staff2.setStartDate(LocalDate.of(2019, 3, 15));
        staff2.setStudent(true);
        Staff staff3 = new Staff("Bruno", "Coenen");
        staff3.setStartDate(LocalDate.of(1995,1,1));
        Staff staff4 = new Staff("Wout", "Dayaert");
        staff4.setStartDate(LocalDate.of(2002, 12, 15));
        Staff staff5 = new Staff("Louis", "Petit");
        staff5.setStartDate(LocalDate.of(2020, 8, 1));
        staff5.setStudent(true);
        Staff staff6 = new Staff("Jean", "Pinot");
        staff6.setStartDate(LocalDate.of(1999,4,1));
        Staff staff7 = new Staff("Ahmad", "Bezeri");
        staff7.setStartDate(LocalDate.of(2009, 5, 1));
        Staff staff8 = new Staff("Hans", "Volzky");
        staff8.setStartDate(LocalDate.of(2015, 6, 10));
        staff8.setStudent(true);
        Staff staff9 = new Staff("Joachim", "Henau");
        staff9.setStartDate(LocalDate.of(2007,9,18));
        staffMembers.add(staff1);
        staffMembers.add(staff2);
        staffMembers.add(staff3);
        staffMembers.add(staff4);
        staffMembers.add(staff5);
        staffMembers.add(staff6);
        staffMembers.add(staff7);
        staffMembers.add(staff8);
        staffMembers.add(staff9);
        return staffMembers;
    }

    private ArrayList<Visitor> fillVisitors() {
        ArrayList<Visitor> visitors = new ArrayList<>();
        Visitor visitor1 = new Visitor("Dominik", "Mioens");
        visitor1.setYearOfBirth(2001);
        Visitor visitor2 = new Visitor("Zion", "Noops");
        visitor2.setYearOfBirth(1996);
        Visitor visitor3 = new Visitor("Maria", "Bonetta");
        visitor3.setYearOfBirth(1998);
        Visitor visitorMe = new Visitor("Michael", "Camerylnck");
        visitorMe.setYearOfBirth(2001);
        visitorMe.addToWishList("De grote golf");
        visitorMe.addToWishList("Piratenboot");
        visitorMe.addToWishList("DreamCatcher");
        visitors.add(visitor1);
        visitors.add(visitor2);
        visitors.add(visitor3);
        visitors.add(visitorMe);

        visitors.get(0).addToWishList("De grote golf");
        visitors.get(0).addToWishList("Sky Scream");
        visitors.get(1).addToWishList("Piratenboot");
        visitors.get(1).addToWishList("Sky Scream");
        visitors.get(1).addToWishList("Halvar the ride");
        visitors.get(1).addToWishList("DreamCatcher");
        visitors.get(2).addToWishList("DinoSplash");
        return visitors;
    }

    private ArrayList<ThemePark> fillThemeParks() {
        ArrayList<ThemePark> themeparks = new ArrayList<>();
        ThemePark themepark1 = new ThemePark("Plopsaland");
        ThemePark themepark2 = new ThemePark("Plopsa Coo");
        ThemePark themepark3 = new ThemePark("Holiday Park");
        Attraction attraction1 = new Attraction("Anubis the Ride", 60);
        Attraction attraction2 = new Attraction("De grote golf", 180);
        Attraction attraction3 = new Attraction("Piratenboot",150);
        Attraction attraction4 = new Attraction("SuperSplash", 258);
        Attraction attraction5 = new Attraction("Dansende fonteinen");
        Attraction attraction6 = new Attraction("Halvar the ride",130);
        Attraction attraction7 = new Attraction("DinoSplash", 240);
        Attraction attraction8 = new Attraction("Bounty Tower", 180);
        Attraction attraction9 = new Attraction("Sky Scream",50);
        attraction1.setPhoto("/img/anubis the ride.jpg");
        attraction2.setPhoto("/img/de grote golf.jpg");
        attraction3.setPhoto("/img/piratenboot.jpg");
        attraction4.setPhoto("/img/supersplash.jpg");
        attraction5.setPhoto("/img/dansende fonteinen.jpg");
        attraction6.setPhoto("/img/halvar the ride.jpg");
        attraction7.setPhoto("/img/dinosplash.jpg");
        attraction8.setPhoto("/img/bountytower.jpg");
        attraction9.setPhoto("/img/sky scream.jpg");
        attraction1.setResponsible(staffMembers.get(0));
        attraction2.setResponsible(staffMembers.get(1));
        attraction3.setResponsible(staffMembers.get(2));
        attraction4.setResponsible(staffMembers.get(3));
        attraction5.setResponsible(staffMembers.get(4));
        attraction6.setResponsible(staffMembers.get(5));
        attraction7.setResponsible(staffMembers.get(6));
        attraction8.setResponsible(staffMembers.get(7));
        attraction9.setResponsible(staffMembers.get(8));
        themepark1.addAttraction(attraction1);
        themepark1.addAttraction(attraction2);
        themepark1.addAttraction(attraction3);
        themepark1.addAttraction(attraction4);
        themepark2.addAttraction(attraction5);
        themepark2.addAttraction(attraction6);
        themepark3.addAttraction(attraction7);
        themepark3.addAttraction(attraction8);
        themepark3.addAttraction(attraction9);
        themeparks.add(themepark1);
        themeparks.add(themepark2);
        themeparks.add(themepark3);
        return themeparks;
    }
}

