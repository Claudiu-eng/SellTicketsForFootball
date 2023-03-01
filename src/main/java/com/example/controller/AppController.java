package com.example.controller;

import com.example.model.dao.*;
import com.example.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    Subscription_teamRepository subscription_teamRepository;

    private User userConnected;
    private Championship championship;
    private Team team;
    private Stadium stadium;
    private Match match;
    private Subscription_team subscription_team;
    private UniversalObject universalObject;
    private final List<Ticket> list2 = new ArrayList<>();
    private final List<Subscription> list3 = new ArrayList<>();

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {

        userConnected=null;
        return "view/homePage";

    }

    @GetMapping(value = "/items/add_championship")
    public String addChamp(Model model){
        if(userConnected==null || userConnected.getRole()!= Role.ADMIN)
            return "view/homePage";
        model.addAttribute("new_championship",new Championship());
        return "view/items/championship_item/create_championship";
    }

    @GetMapping(value = "/items/add_subscription")
    public String addSubs(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        model.addAttribute("new_championship",new Subscription_team());
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("stadiums",stadiumRepository.findAll());
        return "view/items/subscription_item/create_subscription";
    }

    @GetMapping(value = "/items/add_stadium")
    public String addStadium(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        model.addAttribute("new_stadium",new Stadium());
        return "view/items/stadium_item/create_stadium";
    }

    @GetMapping(value = "/items/add_match")
    public String addMatch(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Stadium> list= stadiumRepository.findAll();
        model.addAttribute("stadiums",list);

        List<Team> list1= teamRepository.findAll();
        model.addAttribute("teams",list1);

        return "view/items/match_item/create_match";
    }

    @GetMapping("/client/subs/{id}")
    public String cumparaSubsView( @PathVariable Long id) throws IOException {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        subscription_team=subscription_teamRepository.findById(t);

        List<Seat> list = seatRepository.findAllByStadium_id(subscription_team.getStadiumID());
        for(Seat s:list)
            s.setStatus("Free");
        List<Subscription> subs = subscriptionRepository.findAllByTeamIDAndStadiumID(subscription_team.getTeamID(),subscription_team.getStadiumID());

        for(Subscription s:subs){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID()) {
                    g.setStatus("Ocupat");
                }
            }
        }
        BufferedWriter out = new BufferedWriter(
                new FileWriter("C:\\Cursuri IS\\BileteOnlineUpgraded\\src\\main\\resources\\templates\\view\\client\\myFile.html"));

        if(universalObject==null)
            universalObject=new UniversalObject();

        universalObject.setAttribute1(String.valueOf(subscription_team.getPrice()));

        out.write("<!DOCTYPE html>\n");
        out.write("<html lang=\"en\">\n");
        out.write("<head>\n");
        out.write("<meta charset=\"UTF-8\">\n");
        out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        out.write("<title>Movie Seat Booking</title>\n");
        out.write("<link rel=\"stylesheet\" href=\"style2.css\">\n");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("<div class=\"container\">\n");
        out.write("<form method=\"post\" action=\"/process_cumpara_abonament\" th:object=\"${myObj}\">\n");
        out.write("<button class=\"btnn\">Cumpara</button>\n");
        out.write("<p>Pretul abonamentului pentru un scaun este de : </p>\n");
        out.write("<p  th:text = \"${myObj.attribute1}\"></p>\n");
        out.write("<div class=\"theatre\">\n");

        out.write("<li class=\"row row--1\">\n");
        out.write("<ol class=\"seats\" type=\"A\">\n");
        int i;
        boolean q = false;
        for(i=0;i< list.size();i++){
            out.write("<li class=\"seat\">\n");
            if(!list.get(i).getStatus().equals("Ocupat"))
                out.write("<input type=\"checkbox\"" + " id=\""+list.get(i).getNumber()+"\" name=\"selectedCells\" />\n");
            else {

                out.write("<input type=\"checkbox\"" + " id=\""+list.get(i).getNumber()+" name=\"selectedCells\"   \" checked=\"checked\" onclick=\"return false;\" />\n");
            }
            list.get(i).setStatus("FREE");
            out.write("<label for=\""+list.get(i).getNumber()+"\">"+list.get(i).getNumber()+"</label>\n");
            out.write("</li>\n");
            q=true;
            if((i+1)%10==0){
                out.write("</ol>\n");
                out.write("</li>\n");
                if(i<list.size()-1){
                    out.write("<li class=\"row row--1\">\n");
                    out.write("<ol class=\"seats\" type=\"A\">\n");
                    q=false;
                }
            }
        }

        if(q){
            out.write("</ol>\n");
            out.write("</li>\n");
        }
        out.write("<div class=\"form-group\">\n");
        out.write("<label> Introduce-ti numarul scaunelor pe " +
                " care le doriti,separate prin virgula(,)</label>");
        out.write("<div class=\"input-box\">");
        out.write("<i class=\"fa fa-unlock-alt\" aria-hidden=\"true\"></i>\n");
        out.write("<input type=\"text\" name=\"attribute1\" placeholder=\"Scaune\"  required >\n");

        out.write("</div>\n");
        out.write("</div>\n");
        out.write("</div>\n");
        out.write("</form>\n");
        out.write("</div>\n");
        out.write("</div>\n");
        out.write("</body>\n");
        out.write("</html>\n");
        out.close();

        return "view/erro_page_edit";

    }

    @GetMapping(value = "/client/cumpara/bilet/{id}")
    public String cumparaBiletMeciView(@PathVariable Long id, Model model) throws IOException {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        match=matchRepository.findById(t);
        List<Seat> list = seatRepository.findAllByStadium_id(match.getStadiumID());
        for(Seat s:list)
            s.setStatus("Free");
        List<Ticket> tickets = ticketRepository.findAllByMatchID(match.getId());
        List<Subscription> subs = subscriptionRepository.findAllByTeamIDAndStadiumID(match.getHomeTeamID(),match.getStadiumID());
        for(Ticket s:tickets){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID().intValue())
                    g.setStatus("Ocupat");
            }
        }
        for(Subscription s:subs){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID() && match.getDate_of_match().compareTo(s.getDate_of_end())<0 && match.getDate_of_match().compareTo(s.getDate_of_start())>0)
                    g.setStatus("Ocupat");
            }
        }
        if(universalObject==null)
            universalObject=new UniversalObject();

        universalObject.setAttribute1(String.valueOf(match.getPrice()));

        BufferedWriter out = new BufferedWriter(
                new FileWriter("C:\\Cursuri IS\\BileteOnlineUpgraded\\src\\main\\resources\\templates\\view\\client\\myFile.html"));

        out.write("<!DOCTYPE html>\n");
        out.write("<html lang=\"en\">\n");
        out.write("<head>\n");
        out.write("<meta charset=\"UTF-8\">\n");
        out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        out.write("<title>Seats</title>\n");
        out.write("<link rel=\"stylesheet\" href=\"style2.css\">\n");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("<div class=\"container\">\n");
        out.write("<form method=\"post\" action=\"/process_cumpara_bilet\" th:object=\"${myObj}\">\n");
        out.write("<button class=\"btnn\" id=\"cumpara\">Cumpara</button>\n");
        out.write("<p>Pretul unui bilet este de : </p>\n");
        out.write("<p  th:text = \"${myObj.attribute1}\"></p>\n");
        out.write("<div class=\"theatre\">\n");

        out.write("<li class=\"row row--1\">\n");
        out.write("<ol class=\"seats\" type=\"A\">\n");
        int i;
        boolean q = false;
        for(i=0;i< list.size();i++){
            out.write("<li class=\"seat\">\n");
            if(!list.get(i).getStatus().equals("Ocupat"))
                out.write("<input type=\"checkbox\"" + " id=\""+list.get(i).getNumber()+"\" name=\"selectedCells\" />\n");
            else out.write("<input type=\"checkbox\"" + " id=\""+list.get(i).getNumber()+"\" "+ " name = \"selectedCells\"    checked=\"checked\" onclick=\"return false;\" />\n");
            list.get(i).setStatus("FREE");
            out.write("<label for=\""+list.get(i).getNumber()+"\">"+list.get(i).getNumber()+"</label>\n");
            out.write("</li>\n");
            q=true;
            if((i+1)%10==0){
                out.write("</ol>\n");
                out.write("</li>\n");
                if(i<list.size()-1){
                    out.write("<li class=\"row row--1\">\n");
                    out.write("<ol class=\"seats\" type=\"A\">\n");
                    q=false;
                }
            }
        }

        if(q){
            out.write("</ol>\n");
            out.write("</li>\n");
        }
        out.write("<div class=\"form-group\">\n");
        out.write("<label> Introduce-ti numarul scaunelor pe " +
                " care le doriti,separate prin virgula(,)</label>");
        out.write("<div class=\"input-box\">");
        out.write("<i class=\"fa fa-unlock-alt\" aria-hidden=\"true\"></i>\n");
        out.write("<input type=\"text\" name=\"attribute1\" placeholder=\"Scaune\"  required >\n");

        out.write("</div>\n");
        out.write("</div>\n");
        out.write("</div>\n");
        out.write("</form>\n");
        out.write("</div>\n");
        out.write("</body>\n");
        out.write("</html>\n");
        out.close();


        return "view/erro_page_edit";

    }

    @PostMapping(value = "/process_vezi_locuri")
    public String veziLocuriCumparaBilet(Model model) throws IOException {
        if(userConnected!=null && userConnected.getRole()==Role.ADMIN)
            return "view/homePage";

        model.addAttribute("myObj",universalObject);
        return "view/client/myFile";
    }

    @GetMapping("/items/championship/edit/{id}")
    public String edtiChampView(Model model, @PathVariable Long id){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        championship = championshipRepository.findById(id);
        model.addAttribute("edit_champ",championship);
        return "view/items/championship_item/edit";
    }

    @GetMapping("/items/stadium/edit/{id}")
    public String edtiStadiumView(Model model, @PathVariable Long id){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        stadium = stadiumRepository.findById(id);
        model.addAttribute("edit_champ",stadium);
        return "view/items/stadium_item/edit";
    }

    @GetMapping(value = "/items/team/edit/{id}")
    public String edtiTeamView(Model model, @PathVariable Long id){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        team = teamRepository.findById(id);
        championship = championshipRepository.findById(team.getChampionshipID());
        model.addAttribute("edit_champ",team);
        List<Championship> list=championshipRepository.findAll();
        model.addAttribute("championships",list);

        return "view/items/team_item/edit";
    }

    @GetMapping(value = "/client/echipe/{id}")
    public String campEchipeClient(Model model,@PathVariable Long id){
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        List<Team> list=teamRepository.findAllByChampionshipID(Integer.parseInt(String.valueOf(id)));
        model.addAttribute("teams",list);
        return "view/client/client_echipe";

    }

    @GetMapping(value = "/client/team/meciuri/{id}")
    public String echipaMeciuri(Model model ,@PathVariable Long id) throws ParseException {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";

        Integer t = Integer.parseInt(String.valueOf(id));
        List<Match> list = matchRepository.findAllByHomeTeamIDOrAwayTeamID(t);
        List<UniversalObject> list1=new ArrayList<>();
        for(Match m:list){
            UniversalObject universalObject1=new UniversalObject();
            universalObject1.setAttribute1(String.valueOf(m.getId()));
            universalObject1.setAttribute2(teamRepository.findById(m.getHomeTeamID()).getName());
            universalObject1.setAttribute3(teamRepository.findById(m.getAwayTeamID()).getName());
            universalObject1.setAttribute4(stadiumRepository.findById(m.getStadiumID()).getName());
            universalObject1.setAttribute5(m.getDate_of_match().toString());
            list1.add(universalObject1);
        }
        model.addAttribute("matches",list1);
        return "view/client/client_echipa_meciuri";
    }

    @GetMapping(value = "/items/subscription/edit/{id}")
    public String edtiSubsView(Model model, @PathVariable Long id) {
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        subscription_team = subscription_teamRepository.findById(id);
        model.addAttribute("edit_champ",subscription_team);
        return "view/items/subscription_item/edit";
    }

    @GetMapping("/items/match/edit/{id}")
    public String edtiMatchView(Model model, @PathVariable Long id) throws ParseException {
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        match = matchRepository.findById(id);
        universalObject=new UniversalObject();
        universalObject.setAttribute1(String.valueOf(id));
        universalObject.setAttribute2(teamRepository.findById(match.getHomeTeamID()).getName());
        universalObject.setAttribute3(teamRepository.findById(match.getAwayTeamID()).getName());
        universalObject.setAttribute4(stadiumRepository.findById(match.getStadiumID()).getName());
        universalObject.setAttribute5(match.getDate_of_match().toString());
        model.addAttribute("edit_champ",universalObject);

        List<Stadium> list = stadiumRepository.findAll();
        model.addAttribute("stadiums",list);
        return "view/items/match_item/edit";
    }

    @PostMapping("/edit_match")
    public String editMatch(UniversalObject universalObject1,Model model) throws ParseException {
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        if(stadiumRepository.findById(Long.parseLong(universalObject1.getAttribute4()))==null){
            universalObject.setAttribute6("ID stadion Invalid");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }

        match = matchRepository.findById(Long.parseLong(universalObject.getAttribute1()));
        List<Match> list = matchRepository.findAll();

        for(Match ma: list){
            if(ma.getId().intValue() !=match.getId().intValue())
                if(Integer.parseInt(universalObject1.getAttribute4()) == ma.getStadiumID().intValue())
                    if(ma.getDate_of_match().compareTo(universalObject1.getAttribute5())==0) {
                        universalObject.setAttribute6("Meciurile se disputa la date prea apropiate pe acelasi stadion");
                        model.addAttribute("message", universalObject);
                        return "view/error_page";
                    }
        }

        match.setDate(universalObject1.getAttribute5());
        match.setStadiumID(Long.parseLong(universalObject1.getAttribute4()));
        match.setPrice(Integer.parseInt(universalObject1.getAttribute4()));
        matchRepository.save(match);

        list=matchRepository.findAll();
        model.addAttribute("matches",list);
        return "view/items/match_item/match";
    }



    @PostMapping("/edit_team")
    public String editTeam(Team team1,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Championship championship1 = championshipRepository.findById(team1.getChampionshipID());
        if(championship1==null) {
            if(universalObject==null)
                universalObject=new UniversalObject();
            universalObject.setAttribute6("Echipa introdusa in campionat inexistent");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }
        if(championship1.getNumberOfTeams().intValue()== championship1.getMaximNumberOfTeams().intValue() && championship1.getId().intValue()!=championship.getId().intValue()) {
            if(universalObject==null)
                universalObject=new UniversalObject();
            universalObject.setAttribute6("nu se mai pot adauga echipe in campionatul selectat");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }

        if(!Objects.equals(championship1.getId(), championship.getId())){
            championship.setNumberOfTeams(championship.getNumberOfTeams()-1);
            championshipRepository.save(championship);
            championship1.setNumberOfTeams(championship1.getNumberOfTeams()+1);
            championshipRepository.save(championship1);
        }

        team.setName(team1.getName());
        team.setChampionshipID(team1.getChampionshipID());
        teamRepository.save(team);

        List<Team> list=teamRepository.findAll();
        model.addAttribute("teams",list);
        return "view/items/team_item/team";
    }

    @PostMapping("/edit_stadium")
    public String editStadium(Stadium stadium1,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        if(stadium1.getCapacity() < stadium.getCapacity()){
            List<Seat> seats = seatRepository.findAllByStadium_id(stadium.getId());
            for(Seat s:seats)
                if(s.getNumber()>stadium1.getCapacity())
                    seatRepository.delete(s);
        }else{
            for(int i=stadium.getCapacity();i<stadium1.getCapacity();i++){
                    Seat seat = new Seat();
                    seat.setSeatNumber(i);
                    seat.setStatus("FREE");
                    seat.setStadium_id(stadium.getId());
                    seatRepository.save(seat);
                }
        }

        stadium.setAddress(stadium1.getAddress());
        stadium.setCapacity(stadium1.getCapacity()%100);
        stadium.setName(stadium1.getName());
        stadiumRepository.save(stadium);
        List<Stadium> list=stadiumRepository.findAll();
        model.addAttribute("stadiums",list);
        return "view/items/stadium_item/stadium";
    }

    @PostMapping("/edit_subs")
    public String editSubscr(Subscription_team subscription_team1,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        subscription_team.setPrice(subscription_team1.getPrice());
        subscription_teamRepository.save(subscription_team);
        List<Subscription_team> list = subscription_teamRepository.findAll();
        model.addAttribute("subs",list);
        return "view/items/subscription_item/subscription";
    }

    @PostMapping("/edit_championship")
    public String editChamp(Championship championship1,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        if(championship1.getMaximNumberOfTeams() < championship.getNumberOfTeams()) {
            if(universalObject==null)
                universalObject=new UniversalObject();
            universalObject.setAttribute6("numar prea mic de echipe maxime,mai sterge din echipele din campionat");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }

        championship.setMaximNumberOfTeams(championship1.getMaximNumberOfTeams());
        championship.setName(championship1.getName());
        championshipRepository.save(championship);
        List<Championship> list=championshipRepository.findAll();
        model.addAttribute("championships",list);
        return "view/items/championship_item/championship";
    }

    @GetMapping(value = "/champs/{id}/delete_team")
    public String deleteSomeTeams(Model model,@PathVariable Integer id){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Team> list = teamRepository.findAllByChampionshipID(id);
        model.addAttribute("teams",list);
        model.addAttribute("name",championshipRepository.findById(Long.parseLong(String.valueOf(id))).getName());
        return "view/items/championship_item/delete_some_teams";

    }

    @GetMapping(value = "/subscription/delete/{id}")
    public String deleteSubs(Model model,@PathVariable Long id){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Subscription_team subs = subscription_teamRepository.findById(id);
        if(subs!=null)
            subscription_teamRepository.delete(subs);
        List<Subscription_team> list = subscription_teamRepository.findAll();
        model.addAttribute("subs",list);
        return "view/items/subscription_item/subscription";

    }

    @GetMapping(value = "/team/delete/{id}")
    public String deleteTeam(@PathVariable Integer id,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        team = teamRepository.findById(t);
        championship = championshipRepository.findById(team.getChampionshipID());
        championship.setNumberOfTeams(championship.getNumberOfTeams()-1);
        championshipRepository.save(championship);
        if(team!=null)
            teamRepository.delete(team);
        List<Team> list=teamRepository.findAll();
        model.addAttribute("teams",list);

        return "view/items/team_item/team";
    }

    @GetMapping("/client_campionate")
    public String campionateClient(Model model) {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        List<Championship> list = championshipRepository.findAll();
        model.addAttribute("matches",list);
        return "view/client/client_campionate";
    }

    @GetMapping("/client_echipe")
    public String echipeClient(Model model) {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        List<Team> list = teamRepository.findAll();
        model.addAttribute("teams",list);
        return "view/client/client_echipe";
    }

    @GetMapping(value = "/client_abonament")
    public String subsClient(Model model) throws ParseException {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        List<Subscription_team> list = subscription_teamRepository.findAll();
        List<UniversalObject> list1=new ArrayList<>();
        for(Subscription_team m:list){
            UniversalObject universalObject1=new UniversalObject();
            universalObject1.setAttribute1(String.valueOf(m.getId()));
            universalObject1.setAttribute2(teamRepository.findById(m.getTeamID()).getName());
            universalObject1.setAttribute3(stadiumRepository.findById(m.getStadiumID()).getName());
            universalObject1.setAttribute4(m.getPrice().toString());
            universalObject1.setAttribute5(m.getDate_of_start().toString());
            universalObject1.setAttribute7(m.getDate_of_end().toString());

            list1.add(universalObject1);
        }

        model.addAttribute("subs",list1);
        return "view/client/client_subs";
    }

    @GetMapping("/client_meciuri")
    public String meciuriClient(Model model) throws ParseException {
        if(userConnected!=null && userConnected.getRole()!=Role.CLIENT)
            return "view/homePage";
        List<Match> list = matchRepository.findAll();
        List<UniversalObject> list1=new ArrayList<>();
        for(Match m:list){
            UniversalObject universalObject1=new UniversalObject();
            universalObject1.setAttribute1(String.valueOf(m.getId()));
            universalObject1.setAttribute2(teamRepository.findById(m.getHomeTeamID()).getName());
            universalObject1.setAttribute3(teamRepository.findById(m.getAwayTeamID()).getName());
            universalObject1.setAttribute4(stadiumRepository.findById(m.getStadiumID()).getName());
            universalObject1.setAttribute5(m.getDate_of_match().toString());

            list1.add(universalObject1);
        }
        model.addAttribute("matches",list1);

        return "view/client/client_meciuri";
    }

    @GetMapping(value = "/stadium/delete/{id}")
    public String deleteStadium(@PathVariable Integer id,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        stadium = stadiumRepository.findById(t);
        if(stadium!=null) {
            List<Seat> seats = seatRepository.findAllByStadium_id(t);
            seatRepository.deleteAll(seats);
            stadiumRepository.delete(stadium);
        }
        List<Stadium> list=stadiumRepository.findAll();
        model.addAttribute("stadiums",list);

        return "view/items/stadium_item/stadium";
    }

    @GetMapping(value = "/match/delete/{id}")
    public String deleteMatch(@PathVariable Integer id,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        match = matchRepository.findById(t);
        matchRepository.delete(match);

        List<Match> list=matchRepository.findAll();
        model.addAttribute("matches",list);

        return "view/items/match_item/match";
    }

    @GetMapping(value = "/champs/delete/{id}")
    public String deleteChamp(@PathVariable Integer id,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        Long t = Long.parseLong(String.valueOf(id));
        championship = championshipRepository.findById(t);
        List<Team> list1 = teamRepository.findAll();
        for(Team s:list1)
            if(s.getChampionshipID().intValue() == t.intValue())
                list1.remove(s);
        teamRepository.saveAll(list1);
        championshipRepository.delete(championship);
        List<Championship> list=championshipRepository.findAll();
        model.addAttribute("championships",list);

        return "view/items/championship_item/championship";
    }

    @PostMapping(value = "/create_new_stadium")
    public String insertStadium(Stadium stadium1,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        stadium = new Stadium();
        stadium.setCapacity(stadium1.getCapacity()%100);
        stadium.setName(stadium1.getName());
        stadium.setAddress(stadium1.getAddress());
        stadiumRepository.save(stadium);

        for(int i=0;i<stadium.getCapacity();i++){
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setStatus("FREE");
            seat.setStadium_id(stadium.getId());
            seatRepository.save(seat);
        }

        List<Stadium> list=stadiumRepository.findAll();
        model.addAttribute("stadiums",list);
        return "view/items/stadium_item/stadium";
    }

    @PostMapping(value = "/create_new_subscription")
    public String insertSubs(Subscription_team subscription_team,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        if(teamRepository.findById(subscription_team.getTeamID())==null || stadiumRepository.findById(subscription_team.getStadiumID())==null){
            if(universalObject == null)
                universalObject=  new UniversalObject();
            universalObject.setAttribute6("Echipa sau stadion cu ID invalid");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }

        subscription_teamRepository.save(subscription_team);
        List<Subscription_team> list=subscription_teamRepository.findAll();
        model.addAttribute("subs",list);
        return "view/items/subscription_item/subscription";
    }

    @PostMapping(value = "/create_new_championship")
    public String insertChamp(Championship championship,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        championship.setNumberOfTeams(0);
        championshipRepository.save(championship);
        List<Championship> list=championshipRepository.findAll();
        model.addAttribute("championships",list);
        return "view/items/championship_item/championship";
    }

    @PostMapping(value = "/create_new_match")
    public String insertMatch(Match match,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        if(teamRepository.findById(match.getAwayTeamID())==null || teamRepository.findById(match.getHomeTeamID())==null
            || stadiumRepository.findById(match.getStadiumID())==null){
            if(universalObject == null)
                universalObject=  new UniversalObject();
            universalObject.setAttribute6("Echipa sau stadion cu ID invalid");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }

        List<Match> list = matchRepository.findAll();

        for(Match m:list){
            if(m.getStadiumID().intValue()==match.getStadiumID().intValue())
                if(m.getDate_of_match().toString().equals(match.getDate_of_match().toString())){
                    universalObject.setAttribute6("Meciul se disputa pe un stadion ocupat deja");
                    model.addAttribute("message", universalObject);
                    return "view/error_page";
                }
            if(m.getDate_of_match().toString().equals(match.getDate_of_match().toString())){
                if(m.getAwayTeamID().intValue() == match.getAwayTeamID().intValue() || m.getAwayTeamID().intValue() == match.getHomeTeamID().intValue() ||
                m.getHomeTeamID().intValue()==match.getHomeTeamID().intValue() || m.getHomeTeamID().intValue()==match.getAwayTeamID().intValue()){
                    universalObject.setAttribute6("Meciul are o echipa ocupata la ora selectata");
                    model.addAttribute("message", universalObject);
                    return "view/error_page";
                }
            }
        }

        matchRepository.save(match);
        list=matchRepository.findAll();
        model.addAttribute("matches",list);

        return "view/items/match_item/match";
    }


    @PostMapping(value = "/process_abilities_admin",params = "action=See stadiums")
    public String stadiums(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Stadium> list=stadiumRepository.findAll();
        model.addAttribute("stadiums",list);
        return "view/items/stadium_item/stadium";
    }

    @PostMapping(value = "/process_abilities_admin",params = "action=See subscriptions")
    public String subscripions(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Subscription_team> list=subscription_teamRepository.findAll();
        model.addAttribute("subs",list);
        return "view/items/subscription_item/subscription";
    }

    @PostMapping(value = "/process_abilities_admin",params = "action=See championships")
    public String champions(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Championship> list=championshipRepository.findAll();
        model.addAttribute("championships",list);
        return "view/items/championship_item/championship";
    }


    @PostMapping(value = "/process_abilities_admin",params = "action=See matches")
    public String matches(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Match> list=matchRepository.findAll();
        model.addAttribute("matches",list);
        return "view/items/match_item/match";

    }


    @PostMapping(value = "/create_new_team")
    public String add_team(Team team,Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        championship = championshipRepository.findById(team.getChampionshipID());
        if(universalObject==null)
            universalObject=new UniversalObject();
        if(championship==null) {
            universalObject.setAttribute6("ID campionat invalid");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }
        if(championship.getNumberOfTeams() +1 >championship.getMaximNumberOfTeams()){
            universalObject.setAttribute6("campionat full");
            model.addAttribute("message", universalObject);
            return "view/error_page";
        }
        championship.setNumberOfTeams(championship.getNumberOfTeams()+1);

        teamRepository.save(team);
        championshipRepository.save(championship);
        List<Team> list=teamRepository.findAll();
        model.addAttribute("teams",list);

        return "view/items/team_item/team";
    }

    @PostMapping(value = "/process_abilities_admin",params = "action=See teams")
    public String teams(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Team> list=teamRepository.findAll();
        model.addAttribute("teams",list);

        return "view/items/team_item/team";
    }

    @GetMapping(value = "/items/add_team")
    public String create_team(Model model){
        if(userConnected==null || userConnected.getRole()!=Role.ADMIN)
            return "view/homePage";
        List<Championship> list = championshipRepository.findAll();
        model.addAttribute("new_team",new Team());
        model.addAttribute("championships",list);
        return "view/items/team_item/create_team";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage(){
        return "view/admin/admin_login";
    }

    @GetMapping("/client/login")
    public String showClientLoginPage(){
        return "view/client/client_login";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("registerRequest",new User());
        return "view/register";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {

        user.setRole(Role.CLIENT);
        User user1 = userRepository.findByEmail(user.getEmail());
        if(user1!=null)
            return "view/existing_email_error";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "view/homePage";
    }

    @PostMapping(value = "/process_cumpara_abonament")
    public String cumparaAbonament(Model model,UniversalObject universalObject1){

        String[] locuri =universalObject1.getAttribute1().split(",");
        List<Seat> list = seatRepository.findAllByStadium_id(subscription_team.getStadiumID());
        List<Subscription> subs = subscriptionRepository.findAllByTeamIDAndStadiumID(subscription_team.getTeamID(),subscription_team.getStadiumID());
        for(Subscription s:subs){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID())
                    g.setStatus("Ocupat");
            }
        }
        ArrayList<Long> list1 = new ArrayList<>();
        for(Seat s:list)
            for(String l:locuri){
                if(l.equals(String.valueOf(s.getSeatNumber())))
                    if(!s.getStatus().equals("Ocupat")) {
                        list1.add(s.getId());
                        s.setStatus("FREE");
                    }
            }
        for(Seat s: list)
            if(s.getStatus().equals("Ocupat"))
                s.setStatus("FREE");
        seatRepository.saveAll(list);
        list3.clear();

        for(Long s:list1){
            Subscription t = new Subscription();
            t.setSeatID(s);
            t.setTeamID(subscription_team.getTeamID());
            t.setStadiumID(subscription_team.getStadiumID());
            t.setPrice(subscription_team.getPrice());
            t.setDate_of_start(subscription_team.getDate_of_start());
            t.setDate_of_end(subscription_team.getDate_of_end());
            list3.add(t);
        }

        Integer price = subscription_team.getPrice()*list3.size();
        model.addAttribute("price",price);

        if(userConnected==null)
            return "view/client/client_plateste_nelogat";
        return "view/client/client_plateste";
    }

    @PostMapping(value = "/process_cumpara_bilet")
    public String cumparaBilet(Model model,UniversalObject universalObject1){

        String[] locuri =universalObject1.getAttribute1().split(",");
        List<Seat> list = seatRepository.findAllByStadium_id(match.getStadiumID());
        List<Ticket> tickets = ticketRepository.findAllByMatchID(match.getId());
        List<Subscription> subs = subscriptionRepository.findAllByTeamIDAndStadiumID(match.getHomeTeamID(),match.getStadiumID());
        for(Ticket s:tickets){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID().intValue()) {
                    g.setStatus("Ocupat");
                }
            }
        }
        for(Subscription s:subs){
            for(Seat g:list){
                if(g.getId().intValue()== s.getSeatID() && match.getDate_of_match().compareTo(s.getDate_of_end())<0 && match.getDate_of_match().compareTo(s.getDate_of_start())>0) {
                    g.setStatus("Ocupat");
                }
            }
        }

        ArrayList<Long> list1 = new ArrayList<>();
        for(Seat s:list)
            for(String l:locuri){
                if(l.equals(String.valueOf(s.getSeatNumber())))
                    if(!s.getStatus().equals("Ocupat")) {
                        list1.add(s.getId());
                    }else System.out.println(s.getNumber());
            }
        for(Seat s: list)
            if(s.getStatus().equals("Ocupat"))
                s.setStatus("FREE");
        seatRepository.saveAll(list);

        list2.clear();
        for(Long s:list1){
            Ticket t = new Ticket();
            t.setSeatID(s);
            t.setMatchID(match.getId());
            list2.add(t);
        }

        Integer price = match.getPrice()*list2.size();
        model.addAttribute("price",price);
        System.out.println(list2.size());
        System.out.println(match.getPrice());

        if(userConnected==null)
            return "view/client/client_plateste_nelogat";
        return "view/client/client_plateste";
    }

    @PostMapping(value = "/process_payment")
    public String factura(Model model){
        if(universalObject==null)
            universalObject=new UniversalObject();
        if(list2.size()>0) {
            ticketRepository.saveAll(list2);
            String s="";
            for(Ticket t:list2)
                s=s +","+seatRepository.findById(t.getSeatID()).getNumber();
            universalObject.setAttribute1("Match : "+
                    teamRepository.findById(match.getHomeTeamID()).getName()+
                    " vs "+teamRepository.findById(match.getAwayTeamID()).getName());
            universalObject.setAttribute3(match.getDate_of_match().toString());
            universalObject.setAttribute4(match.getDate_of_match().toString());
            universalObject.setAttribute2(s);
            model.addAttribute("message",universalObject);
            list2.clear();
            return "view/client/bill";
        }
        if(list3.size()>0) {
            subscriptionRepository.saveAll(list3);
            String s="";
            for(Subscription t:list3)
                s=s +","+seatRepository.findById(t.getSeatID()).getNumber();
            universalObject.setAttribute1("Subscription : "+
                    teamRepository.findById(subscription_team.getTeamID()).getName()+
                    " to stadium "+stadiumRepository.findById(subscription_team.getStadiumID()).getName());
            universalObject.setAttribute3(subscription_team.getDate_of_start().toString());
            universalObject.setAttribute4(subscription_team.getDate_of_end().toString());
            universalObject.setAttribute2(s);
            model.addAttribute("message",universalObject);
            list3.clear();
            return "view/client/bill";

        }

        return "view/homePage";
    }

    @PostMapping(value = "/process_login_client",params = "action=guest")
    public String connectGuest(){
        userConnected = null;
        return "view/client/client_page";
    }

    @PostMapping(value = "/process_login_client" ,params = "action=login")
    public String processLogInClient(User user,Model model){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user1 = userRepository.findByEmail(user.getEmail());
        userConnected=user1;
        if(universalObject==null)
            universalObject=new UniversalObject();
        if(user1==null){
            universalObject.setAttribute6("Cont inexistent");
            model.addAttribute("message",universalObject);
            return "view/error_page";
        }
        CharSequence charSequence = user.getPassword().subSequence(0,user.getPassword().length());
        if(!passwordEncoder.matches(charSequence,user1.getPassword())){
            universalObject.setAttribute6("credentiale gresite");
            model.addAttribute("message",universalObject);
            return "view/error_page";
        }

        if(user1.getRole().equals(Role.CLIENT)) {
            return "view/client/client_page";
        }
        universalObject.setAttribute6("alta eroare");
        model.addAttribute("message",universalObject);
        return "view/error_page";
    }
    @PostMapping("/process_login_admin")
    public String processLogInAdmin(User user,Model model){
        User user1 = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        userConnected=user1;
        if(universalObject==null)
            universalObject=new UniversalObject();
        if(user1==null) {
            universalObject.setAttribute6("admin invalid");
            model.addAttribute("message",universalObject);
            return "view/error_page";
        }
        if(user1.getRole().equals(Role.ADMIN)) {
            return "view/admin/admin_page";
        }
        universalObject.setAttribute6("admin invalid");
        model.addAttribute("message",universalObject);
        return "view/error_page";
    }

}
