<template>
  <v-form ref="form">
    <v-container>
      <v-data-table
        :headers="headers"
        :items="instrument"
        class="elevation-1 ml-2"
        hide-default-footer
        dense
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>관심 악기</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <!--추가 모달-->
            <v-dialog v-model="dialog" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="teal" dark class="mb-2" v-bind="attrs" v-on="on">
                  추가
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">{{ formTitle }}</span>
                </v-card-title>

                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="6" md="4">
                        <v-autocomplete
                          v-model="editedItem.name"
                          :items="instruments"
                          no-data-text="찾으시는 악기가 없습니다"
                          label="악기"
                        ></v-autocomplete>
                      </v-col>

                      <v-col cols="12" sm="6" md="4">
                        <v-select
                          :items="grades"
                          v-model="editedItem.grade"
                          label="등급"
                        ></v-select>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="close">
                    Cancel
                  </v-btn>
                  <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!--삭제 모달-->
            <v-dialog v-model="dialogDelete" max-width="500px">
              <v-card>
                <v-card-title class="headline">삭제?</v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="red darken-1" text @click="closeDelete"
                    >아니요</v-btn
                  >
                  <v-btn color="teal darken-1" text @click="deleteItemConfirm"
                    >네</v-btn
                  >
                  <v-spacer></v-spacer>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>

        <template v-slot:item.actions="{ item }">
          <v-icon medium class="mr-2" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon medium class="mr-n2" @click="deleteItem(item)">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </v-container>
  </v-form>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    dialogDelete: false,
    grades: ["초급", "중급", "고급"],
    instruments: ["피아노", "기타", "전자피아노"],
    headers: [
      {
        text: "악기",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "등급", value: "grade" },
      { text: "수정 / 삭제", value: "actions", sortable: false },
    ],
    instrument: [],
    editedIndex: -1,
    editedItem: {
      name: "",
      grade: 0,
    },
    defaultItem: {
      name: "",
      grade: 0,
    },
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "악기 추가" : "악기 수정";
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.instrument = [];
    },

    editItem(item) {
      this.editedIndex = this.instrument.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.instrument.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.instrument.splice(this.editedIndex, 1);
      this.closeDelete();
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.instrument[this.editedIndex], this.editedItem);
      } else {
        this.instrument.push(this.editedItem);
      }

      this.emitEvent();
      this.close();
    },
    emitEvent() {
      this.$emit("formSend", this.instrument);
    },
  },
};
</script>
<style>
</style>