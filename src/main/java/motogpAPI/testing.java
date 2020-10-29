package motogpAPI;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import motogp.controller.motogpdata.MotoGPData;
import motogp.model.*;

public class testing {

	public static void main(String[] args) {

				MotoGPData data = new MotoGPData();
				List<RiderOnlineData> qp2 = data.getResultsByRaceCode(Category.MotoGP, 2019, RaceCode.ESP, Session.QP2);
				System.out.println(qp2);
				}
			
			}
