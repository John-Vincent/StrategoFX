SRC = ./stratego/
BIN = ./bin/
RM = rm -rf
ECHO = echo

JC = javac
CP := -cp .:bin/
FLAGS = -Werror -d $(BIN) $(CP) -Xlint


APPLICATION = $(addprefix $(SRC)application/, StrategoFX Background )

COMPONENTS = $(addprefix $(SRC)components/, Sizes friendslist/FriendModel friendslist/FriendsList gameboard/boardPiece gameboard/GameScene gameboard/Logic )

MENUS = $(addprefix menus/, main/MainMenuUI main/MainMenuWorker settings/SettingsMenuUI settings/SettingsMenuWorker login/LoginMenuUI login/LoginMenuWorker signup/SignupMenuUI signup/SignupMenuWorker )
MULTIPLAYER = $(addprefix multiplayer/, MultiplayerUI MultiplayerWorker )
SINGLEPLAYER = $(addprefix singleplayer/, SingleplayerUI SingleplayerWorker )
MODE = $(addprefix $(SRC)mode/, Mode ModeWorker $(MENUS) $(MULTIPLAYER) $(SINGLEPLAYER) )

NETWORK = $(addprefix $(SRC)network/, Networker )

SERVER = $(addprefix $(BIN)$(SRC)server/, StrategoServer PacketHandler DBManager )
SERVER := $(addsuffix .class, $(SERVER) )

SOURCES = $(addsuffix .class, $(APPLICATION) $(MODE) $(NETWORK) $(COMPONENTS))
SOURCES := $(addprefix $(BIN), $(SOURCES))

RESOURCES := $(addprefix $(BIN), $(SRC)components/gameboard/images)


default: makebin $(SOURCES) $(RESOURCES)
	@$(ECHO) "  "
	@$(ECHO) "Compilation complete"

server: CP = -cp .:bin/:commons-logging-1.2.jar:mysql-connector-java-5.1.20-bin.jar:commons-dbcp2-2.1.1.jar
server: makebin $(SERVER)
	@$(ECHO) "  "
	@$(ECHO) "Compilation complete"


$(BIN)%.class: %.java
	@$(ECHO) "  "
	@$(ECHO) compiling $<
	@$(JC) $(FLAGS) $<
	@$(ECHO) $@ compiled successfully

$(BIN)%: %
	@cp -r $< $@

.PHONY: clean makebin default server resources

resources: $(RESOURCES)
	@cp -r

clean:
	@$(ECHO) "removing binaries"
	@[ ! -d $(BIN) ] || $(RM) $(BIN)

makebin:
	@[  -d $(BIN) ] || $(ECHO) "making bin folder"
	@[  -d $(BIN) ] || mkdir $(BIN)
