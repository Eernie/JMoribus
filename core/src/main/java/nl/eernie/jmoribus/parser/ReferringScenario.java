package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.StepContainer;

class ReferringScenario extends Scenario
{
	public ReferringScenario(String combinedStepLines, StepContainer prologueOrScenario)
	{
		this.setStepContainer(prologueOrScenario);
		this.setTitle(combinedStepLines);
	}
}
