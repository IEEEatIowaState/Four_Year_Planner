var reqsRaw = {"CprE 281" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : " ",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "4",
		"Minimum classification" : "Sophomore"
	},
	"CprE 288" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "CprE 281",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "4"
	},
	"CprE 308" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "CprE 381",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "4"
	},
	"CprE 310" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "ComS 228",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "3"
	},
	"CprE 381" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "CprE 288",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "4"
	},
	"ComS 309" : {
		"Department" : "ComS",
		"Status" : "Completed",
		"Pre Requirements" : "ComS 228",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "3"
	},
	"ComS 311" : {
		"Department" : "ComS",
		"Status" : "Completed",
		"Pre Requirements" : "ComS 228",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "3"
	},
	"EE 201" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "",
		"Co Requirements" : "Math 267",
		"Other Information" : " ",
		"Credit hours" : "4"
	},
	"EE 230" : {
		"Department" : "EcpE",
		"Status" : "Completed",
		"Pre Requirements" : "EE 201",
		"Co Requirements" : " ",
		"Other Information" : " ",
		"Credit hours" : "4"
	}
};
function addClasses() {

    var cList = document.getElementById("classList");
    var nOption = document.createElement("option");
	//var reqs = JSON.parse(reqsRaw);
    
    var names = Object.keys(reqsRaw);
    var name;
    for(name in names) {
        var nOption = document.createElement("option");
        nOption.text = names[name];
        nOption.value = names[name];
        cList.add(nOption);
    }

    /* TODO remove

    nOption.text = "CPRE 381";
    nOption.value = "cpre381";
    cList.add(nOption);
    
    //var nOption = document.createElement("option");
	nOption.text = names[0];
    nOption.value = names[0];
    cList.add(nOption);
    */
}
