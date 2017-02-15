BIN = ./bin/
SRC = ./stratego/
RM = rm -rf
ECHO = echo

JC = javac
FLAGS = -Werror -d $(BIN) -cp .

APPLICATION = $(addprefix $(SRC)application/, Background StrategoFX )

COMPONENTS = $(addprefix $(SRC)components/, Sizes)

MENUS = $(addprefix menus/, main/MainMenuUI main/MainMenuWorker settings/SettingsMenuUI settings/SettingsMenuWorker )
MULTIPLAYER = $(addprefix multiplayer/, MultiplayerUI MultiplayerWorker )
SINGLEPLAYER = $(addprefix singleplayer/, SingleplayerUI SingleplayerWorker )
MODE = $(addprefix $(SRC)mode/, $(MENUS) $(MULTIPLAYER) $(SINGLEPLAYER) Mode ModeWorker )

NETWORK = $(addprefix $(SRC)network/, Networker )

SOURCES = $(addsuffix .class, $(APPLICATION) $(MODE) $(NETWORK) $(COMPONENTS))
SOURCES := $(addprefix $(BIN), $(SOURCES))


default: makebin $(SOURCES) wtf
	@$(ECHO) "  "
	@$(ECHO) "Compilation complete"

$(BIN)%.class: %.java
	@$(ECHO) "  "
	@$(ECHO) compiling $<
	@$(JC) $(FLAGS) $<
	@$(ECHO) $@ compiled successfully



.PHONY: clean makebin default wtf

clean:
	@$(ECHO) "removing binaries"
	@[ ! -d $(BINARIES) ] || $(RM) $(BINARIES)

makebin:
	@[ -d $(BINARIES) ] || $(ECHO) "making bin folder"
	@[ -d $(BINARIES) ] || mkdir $(BINARIES)
