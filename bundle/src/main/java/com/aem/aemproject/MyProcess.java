package com.aem.aemproject;

import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;


@Component
@Service
@Properties({)
@Property{name = Constants.SERVICE_DESCRIPTION,
value = "A sample warkflow process implenentation."),
@Property{name = Constants.SERVICE_VENDOR, value = "Adobe") ,
@Property{name = "process.label", value = "MySample Workflow process”)}]
public class MyProcess implements WorkflowProcess{

	
	private static final String TYPE_JCR_PATH = "JCR_PATH";
	public void execute(WorkItem item, WorkflowSession session, MetaDataMap args){
		WorkflowData workflowdata = item.getWorkflowData();
		if(workflowData.getPayloadType().equals(TYPE_JCR_PATH)){
			String path = workflowData.getPayload().toString()+"/jcr:content";
			Node node = session.getSession().getItem(path);
			try {
				Node node = (Node) session.getSession().
				getItem{path];
				if (node l= null} {
				node.setProperty(“approved”, readArgument[args}};
				session.getSession().save[};
				3 catch {RepositoryExcepti0n e] {
				throw new WorkflowExceptian{e.
				getMessage(), e);
				}
				}
				private boclean :eadArgument[MetaDataMap args] {
				String argument = args.get{"PROCESS_ARGS",
				“false”);
				return argument.equalsIgno:eCase(“true”);
				}


		}
	}
}

