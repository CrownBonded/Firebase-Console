public class AppIndexingService extends IntentService {
...
   @Override
   protected void onHandleIntent(Intent intent) {
       ArrayList<Indexable> indexableNotes = new ArrayList<>();

       for (Recipe recipe : getAllRecipes()) {
           Note note = recipe.getNote();
           if (note != null) {
               Indexable noteToIndex = Indexables.noteDigitalDocumentBuilder()
                       .setName(recipe.getTitle() + " Note")
                       .setText(note.getText())
                       .setUrl(recipe.getNoteUrl())
                       .build();

               indexableNotes.add(noteToIndex);
           }
       }

       if (indexableNotes.size() > 0) {
           Indexable[] notesArr = new Indexable[indexableNotes.size()];
           notesArr = indexableNotes.toArray(notesArr);

           // batch insert indexable notes into index
           FirebaseAppIndex.getInstance().update(notesArr);
       }
   }
...
}
