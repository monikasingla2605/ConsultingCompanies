package com.scg.domain;

/**
 * This is enum class which defines the skills of consultants.
 * @author Monika
 *
 */
public enum Skill
{
    PROJECT_MANAGER("Project Manager", 250),
    SYSTEM_ARCHITECT("System Architect", 200),
    SOFTWARE_ENGINEER("Software Engineer", 150),
    SOFTWARE_TESTER("Software Tester", 100),
    UNKNOWN_SKILL("Unknown Skill", 0);

    /**
     * Rate of the skill set to be charged.
     */
    private int rate;
    private String description;

    private Skill(String description, int rate)
    {
        this.rate = rate;   
        this.description = description;
    }


    /**
     * This gets the rate for any particular skill.
     * @return Returns the rate for any skill.
     */
    public int getRate()
    {
        return rate;
    }

    /*
     * This method over rides the tostring method.
     * It returns the description of the skill.
     *  (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return this.description;
    }

}
