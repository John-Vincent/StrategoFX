BIN = ./bin/
SRC = ./stratego/
RM = rm -rf
ECHO = echo

JC = javac
FLAGS = -Werror -d $(BIN) -cp .:bin/commons-dbcp2-2.1.1.jar

APPLICATION = $(addprefix $(SRC)application/, StrategoFX Background )

COMPONENTS = $(addprefix $(SRC)components/, Sizes)

MENUS = $(addprefix menus/, main/MainMenuUI main/MainMenuWorker settings/SettingsMenuUI settings/SettingsMenuWorker login/LoginMenuUI login/LoginMenuWorker signup/SignupMenuUI signup/SignupMenuWorker )
MULTIPLAYER = $(addprefix multiplayer/, MultiplayerUI MultiplayerWorker )
SINGLEPLAYER = $(addprefix singleplayer/, SingleplayerUI SingleplayerWorker )
MODE = $(addprefix $(SRC)mode/, Mode ModeWorker $(MENUS) $(MULTIPLAYER) $(SINGLEPLAYER) )

NETWORK = $(addprefix $(SRC)network/, Networker )

SERVER = $(addprefix $(BIN)$(SRC)server/, StrategoServer PacketHandler )
SERVER := $(addsuffix .class, $(SERVER) )

SOURCES = $(addsuffix .class, $(APPLICATION) $(MODE) $(NETWORK) $(COMPONENTS))
SOURCES := $(addprefix $(BIN), $(SOURCES))




default: makebin $(SOURCES)
	@$(ECHO) "  "
	@$(ECHO) "Compilation complete"

server: makebin $(SERVER)
	@$(ECHO) "  "
	@$(ECHO) "Compilation complete"

$(BIN)%.class: %.java
	@$(ECHO) "  "
	@$(ECHO) compiling $<
	@$(JC) $(FLAGS) $<
	@$(ECHO) $@ compiled successfully



.PHONY: clean makebin default server

clean:
	@$(ECHO) "removing binaries"
	@[ ! -d $(BIN) ] || $(RM) $(BIN)

makebin:
	@[  -d $(BIN) ] || $(ECHO) "making bin folder"
	@[  -d $(BIN) ] || mkdir $(BIN)
