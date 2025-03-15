/* 
    CLI FOR DENO
    deno run --allow-net --allow-read https://pastefy.app/cli.js (filename)
*/
console.log("Pasting...");

let data = new FormData();

Deno.readTextFile(Deno.args[0]).then(contents => {
    data.append("title", Deno.args[0]);
    data.append("content", contents);

    fetch("https://pastefy.app/create:paste", {
        method: "POST",
        body: data
    }).then((res)=>{
        res.json().then(res=>{
            console.log("Pasted at: https://pastefy.app/"+res.id)
        });
    })
})