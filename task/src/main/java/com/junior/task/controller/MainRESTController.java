package com.junior.task.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.junior.task.DAO.PurchaseEntityRepository;
import com.junior.task.model.PurchaseEntity;
import com.junior.task.model.Purchase_item;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

@RestController
public class MainRESTController  {

    private PurchaseEntityRepository purchaseDAO;

    public MainRESTController(PurchaseEntityRepository purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

	@GetMapping(value = "/get", //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<PurchaseEntity> getPE() {
        return purchaseDAO.findAll();
	}


	// URL:
	// http://localhost:8080/SomeContextPath/employee
	// http://localhost:8080/SomeContextPath/employee.xml
	// http://localhost:8080/SomeContextPath/employee.json

	@PostMapping(value = "/add")
	@ResponseBody
	public PurchaseEntity addPE(@RequestParam("name") String name,
								@RequestParam("Last_name") String last_name,
								@RequestParam("age") int age,
								@RequestParam("Purchase_items") List<Purchase_item> Purchase_items,
								@RequestParam("count") int count,
								@RequestParam("amount") int amount,
								@RequestParam("time") String t)
	{
		System.out.println(System.getProperty("user.dir"));
        PurchaseEntity PE = new PurchaseEntity(name,last_name,age
        ,Purchase_items, count,amount,t);
		return purchaseDAO.save(PE);
	}

	// URL:
	// http://localhost:8080/SomeContextPath/employee
	// http://localhost:8080/SomeContextPath/employee.xml
	// http://localhost:8080/SomeContextPath/employee.json
	@PostMapping("/addxml")
	public ResponseEntity updatePE(@RequestBody String xmlMapper) {

        XmlMapper xmlMap = new XmlMapper();
		PurchaseEntity object = new PurchaseEntity();
        try {
            object = xmlMap.readValue(xmlMapper, PurchaseEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println(xmlMapper);
		try {
			System.out.println("Validate XML against XSD Schema");
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File("C:schema.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xmlMapper)));
			System.out.println("Validation is successful ");

			List<Purchase_item> pi = new ArrayList<>();

			PurchaseEntity pe = new PurchaseEntity(object.getName(),object.getLastname(),object.getAge(),object.getPurchase_items(),
					object.getCount(),object.getAmount(), object.getTime());
			for(int i = 0; i < object.getPurchase_items().size();i++){
				Purchase_item item = new Purchase_item(object.getPurchase_items().get(i).getUrl(),
						object.getPurchase_items().get(i).getName(),
						pe);
				pi.add(item);
			}
			pe.setPurchase_items(pi);
			System.out.println(pe.getPurchase_items().get(0).getName());
			purchaseDAO.save(pe);
			return ResponseEntity.status(HttpStatus.OK).body("OK");
		}  catch (SAXException e) {
			System.out.println("Error when validate XML against XSD Schema");
			System.out.println("Message: " + e.getMessage());
		}  catch (IOException e) {
				e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) ;
	}
}

