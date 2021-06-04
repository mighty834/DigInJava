package engine;
import engine.exceptions.*;
import engine.Commander.Commands;
import aux_tools.*;
import java.util.Arrays;

class KeyKeeper {
	//Main keys
    public static final String BUILD_ASSETS          = "-ba";
    public static final String CREATE_NEW_EX         = "-cne";
    public static final String SHOW_DEFINED_TOPICS   = "-sdt";
    public static final String FIND_EXERCISES        = "-fe";
    public static final String EX_KEY                = "--";

    //Additional keys
    public static final String FIND_EX_BY_TOPIC      = "-t";
    public static final String DETAIL_KEY            = "-d";

	private static Commands defineShowDefinedTopics(String[] keys) {
		return (Arrays.asList(keys).contains(DETAIL_KEY)) ?
				Commands.SHOW_DEFINED_TOPICS_DETAIL :
				Commands.SHOW_DEFINED_TOPICS;
	}

	private static Commands defineFindExercises(String[] keys) {
		return Commands.FIND_EXERCISES_BY_TOPIC;
	}

	public static Commander.Commands getCommand(String[] keys) throws MainException {
		Commands command = null;
		String mainKey = ""; 
        String[] additionalKeys = {""};
		if (keys.length > 0) mainKey = keys[0];
		if (keys.length > 1) additionalKeys = Arrays.copyOfRange(keys, 1, keys.length);

		if (mainKey.contains(EX_KEY)) {
			command = Commands.EXERCISE_RUN;
		} else {
			switch (mainKey) {
				case BUILD_ASSETS : {
					if (additionalKeys[0] == null) throw new MissingBuildPath(); 
					command = Commands.BUILD_ASSETS;
				}
				break;
			
				case CREATE_NEW_EX : {
					if (additionalKeys[0] == null) throw new WrongArguments(CREATE_NEW_EX);
					command = Commands.CREATE_NEW_EX;
				}
				break;

				case SHOW_DEFINED_TOPICS : {
					command = defineShowDefinedTopics(additionalKeys);	
				}
				break;

				case FIND_EXERCISES : {
					command = defineFindExercises(additionalKeys);	
				}
				break;

				default : throw new WrongCommand(mainKey); 
			}
		}

		return command;
	}
}

