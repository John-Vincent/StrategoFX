BIN = ./bin/
SRC = ./stratego/
RM = rm -rf
ECHO = echo

JC = javac
FLAGS = -Werror -d $(BIN) -cp .

APPLICATION = $(addprefix $(SRC)application/, Background StrategoFX )

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

$(BIN)%.class: %.java
	@$(ECHO) "  "
	@$(ECHO) compiling $<
	@$(JC) $(FLAGS) $<
	@$(ECHO) $@ compiled successfully



.PHONY: clean makebin default server

clean:
	@$(ECHO) "removing binaries"
	@[ ! -d $(BINARIES) ] || $(RM) $(BINARIES)

makebin:
	@[ -d $(BINARIES) ] || $(ECHO) "making bin folder"
	@[ -d $(BINARIES) ] || mkdir $(BINARIES)
