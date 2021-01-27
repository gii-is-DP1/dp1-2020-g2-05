package org.springframework.samples.petclinic.web;

public class PoblarBaseDeDatosControllerTest {
	
//	private static final Integer TEST_ACTUAL_YEAR = 2021;
//
//
//	 @MockBean
//	 @Autowired
//	 private UserService userService;
//	 
//	 @MockBean
//	 @Autowired
//	 private LeagueService leagueService;
//	 
//	 @MockBean
//	 @Autowired
//	 private GranPremioService GPService;
//	 
//
//	 @Autowired
//	private MockMvc mockMvc;
//	 
//	 GranPremio gp = new GranPremio();
//	 List<GranPremio>  gps = new ArrayList<GranPremio>();
//	 Set<Lineup> lineups = new HashSet<>();
//	 Record record = new Record();
//	 Lineup lineup = new Lineup();
//	 Set<Result> results = new HashSet<>();
//	 Result result = new Result();
//
//		@BeforeEach
//		void setup() throws DataAccessException, duplicatedLeagueNameException, ParseException {
//			
//			gp.setCalendar(true);
//			gp.setCircuit("LUCENA");
//			gp.setDate0(LocalDate.of(2021, 06, 12));
//			gp.setHasBeenRun(false);
////			gp.setId(1234567);
//			gp.setRaceCode("QAT");
//			gp.setSite("España");
////			gp.setRecord(record);
////			lineups.add(lineup);
////			gp.setLineups(lineups);
////			results.add(result);
////			gp.setResults(results);
//			System.out.println("awidnawd");
//			System.out.println(gp);
////			this.GPService.saveGP(gp);
//			this.GPService.findAllActualYear(2021);
//			gps.add(gp);
//			System.out.println(gps);
//			
//			given(GPService.findAllActualYear(2021)).willReturn(gps);
//		}
//	  @WithMockUser(value = "spring")
//	  @Test
//		void testPoblar() throws Exception {
//		  
//		 
//		  given(this.GPService.findAllActualYear(TEST_ACTUAL_YEAR)).willReturn(gps);
//			mockMvc.perform(get("/controlPanel"))
//					.andExpect(status().isOk())
//					.andExpect(model().attributeExists("listaGP"))
//					.andExpect(model().attribute("listaGP", Matchers.hasItem(Matchers.<GranPremio> hasProperty("site", is(gp.getSite())))))
//					.andExpect(view().name("/panelControl/panelDeControl"));
//		}
}
		

